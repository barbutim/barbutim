<?php
    include('includes/_fileDB.php');  // Zalinkovanie údajov z databázy a funkcií
    session_start();

    $alert = False;
    $classCounter = 'n0';
    $popUpCounter = 'p0';
    $closeCounter = 'c0';
    $counter = 0;

    if(isset($_SESSION['dayVal']) && isset($_SESSION['monthVal']) && isset($_SESSION['yearVal'])){
      $dayV = $_SESSION['dayVal'];  // Príjmanie hodnôt z calendar.js -> _getValuesFromJS.php -> $_SESSION
      $monthV = $_SESSION['monthVal'];
      $yearV = $_SESSION['yearVal'];
    }

    else{
      $dayV = '';
      $monthV = '';
      $yearV = '';
    }

    if(isset($_POST['deleteNote'])){  // Ak užívateľ klikne na tlačidlo vymazania poznámky -> vymaže poznámku
      deleteNote($data, $_POST['deleteNote']);
      header("Location: main.php");
    }

    if(isset($_POST['filter'])){  // Ak užívateľ vyplní filter v hlavičke dokumentu
      $filterWithUppercase = htmlspecialchars($_POST['filter']);  // Ošetrenie vstupu
      $filter = strtolower($filterWithUppercase); // Vstup -> lowercase
    }

    if(isset($_SESSION['prevFilter'])){ // Ak je v $_SESSION['prevFilter'] hodnota...
      $previousFilter = $_SESSION['prevFilter'];  // Zapíše predchádzajúcu hodnotu filtra do $previousFilter
    }

    if(isset($_POST['searchInDb'])){  // Ak bolo stlačené tlačidlo pre filter v hlavičke dokumentu
      if($previousFilter != $filter && $filter != ''){  // Ak užívateľ odošle 2x a viac po sebe rovnakú hodnotu,
        $previousFilter = $filter;  // filter už fungovať nebude
        $calendar = False;
      }

      else{
        $calendar = True;
        $alert = True;
      }
    }

    else{
      $previousFilter = '';
      $calendar = True;
    }

    $_SESSION['prevFilter'] = $previousFilter;  // Uloženie hodnoty predchádzajúceho filtra do $_SESSION['prevFilter']
    $mainTitle = 'Kalendár';
    include('includes/_header.php');  // Zalinkovanie hlavičky dokumentu
?>
<article>
  <div class="calendar">
    <div class="top"> <!-- Aktuálny dátum, mesiac, smerové šípky -->
      <h2 class="previousMonth">&#171;</h2>
      <div class="currentDate">
        <h2>Mesiac</h2>
        <p>Dátum</p>
      </div>
      <h2 class="nextMonth">&#187;</h2>
    </div>
    <nav>
      <ul>
        <li class="daysInaWeek">  <!-- Výpis Pon-Ned -->
          <a>Pon</a>
          <a>Uto</a>
          <a>Str</a>
          <a>Štv</a>
          <a>Pia</a>
          <a>Sob</a>
          <a>Ned</a>
        </li>
        <li class="numberOfDays"></li>  <!-- Výpis dní v mesiaci -->
      </ul>
    </nav>
    <p class="createButton"><a href="addForm.php" class="button">Vytvoriť</a></p> 
  </div>
  <div class="notes">
    <nav>
      <ul>
        <?php // Výpis údajov z databázy
        if($data != 'Databáza nie je funkčná!'){  // Ak nie je funkčná (zalinkovaná) databáza -> zobraz chybovú hlášku namiesto poznámok
          $limit = 3; // Poznámky na stránku
          $userNoteTotal = [];
          foreach($data['notes'] as $note){ // Prechádzaj všetky užívateľove poznámky
            $text = htmlspecialchars($note['text']);
            $title = htmlspecialchars($note['title']);
            $time = htmlspecialchars($note['time']);
            $date = htmlspecialchars($note['date']);
            $id = htmlspecialchars($note['id']);
            // Po zakliknutí tlačidla v kalendáry, sa zakliknuté údaje porovnajú s údajmi v databáze, pričom sa do arrayListu zapíšu iba tie, ktoré vyhovujú dátumu
            if($dayV == substr($date, 8, 2) && $monthV == substr($date, 5, 2) && $yearV == substr($date, 0, 4) && $calendar == True){
              array_push($userNoteTotal, $note);
            }
            if(isset($filter)){ // Po odoslaní filtra v hlavičke dokumentu sa do arrayListu zapíšu iba tie, s hľadanou hodnotou v názve
              $title = strtolower($title);  // $title -> lowercase, teda predtým taktiež $filter -> lowercase,
                                            // aby pri porovnaní hodnôt nezáležalo na veľkých a malých písmenkách
              if($filter == $title && $calendar == False){
                array_push($userNoteTotal, $note);
              }
            }
          }
          if(count($userNoteTotal) != 0){ // Ak vyhovuje filtru aspoň jedna poznámka... ak nie -> zobraz chybovú hlášku
            $pages = ceil(count($userNoteTotal)/$limit);  // Výpočet počtu zobrazovaných stránok
            $page = min($pages, filter_input(INPUT_GET, 'page', FILTER_VALIDATE_INT, array(
              'options' => array(
                'default'   => 1,
                'min_range' => 1,
              ),
            )));
            $offset = ($page - 1) * $limit; // Výpočet offsetu
            
            $n = [];
            $last = min(($offset + $limit),count($userNoteTotal));
            for($i = $offset; $i < $last; $i++){  // Získanie poznámok od offsetu, napr. ak som na druhej stránke, do $n zapíš poznámky 3-5 (podľa indexu)
              array_push($n, $userNoteTotal[$i]);
            }

            $note = $n;
            foreach($note as $n){ // Prechádzaj aktuálne 3 poznámky a zobraz ich
              $text = htmlspecialchars($n['text']);
              $title = htmlspecialchars($n['title']);
              $time = htmlspecialchars($n['time']);
              $date = htmlspecialchars($n['date']);
              $id = htmlspecialchars($n['id']);
              $fullDate = substr($date, 8, 2) . '.' . substr($date, 5, 2) . '.' . substr($date, 0, 4);
              // Pridanie poznámky + PopUp window pre vypísanie textu poznámky, ktoré je defaultne schované a zobrazí sa až po kliknutí na poznámku
              $addNote = '<li class="popup ' . $popUpCounter . '"><div>
                <p class="close ' . $closeCounter . '">+</p>
                <form>
                  <label class="note">
                    <span class="textPopup">Text poznámky:</span>
                    <textarea class="show" disabled>' . $text . '</textarea>
                  </label>
                </form>
              </div></li>
              <li class="noteAdd"><a class="' . $classCounter . '"><div><p class="time">' . $time . '</p><p class="time timeStamp">' . $fullDate . '</p></div><p class="title">' . $title . '</p></a><form method=\'post\'>
              <button class=\'del noteButton\' name=\'deleteNote\' value=\'' . $id . '\'>Vymazať</button></form>
              <form method=\'post\' action=\'addForm.php\'><button class=\'upd noteButton\' name=\'editNote\' value=\'' . $id . '\'>Upraviť</button></form></li>';

              // Po zakliknutí tlačidla v kalendáry, sa zakliknuté údaje porovnajú s údajmi v databáze a vypíšu sa iba tie, ktoré vyhovujú dátumu
              if($dayV == substr($date, 8, 2) && $monthV == substr($date, 5, 2) && $yearV == substr($date, 0, 4) && $calendar == True){
                $counter += 1;
                $classCounter = $classCounter . $counter; // Classy, aby bolo možné jednotlivým výpisom na stránke, priradil onClick action cez JS
                $popUpCounter = $popUpCounter . $counter;
                $closeCounter = $closeCounter . $counter;
                echo $addNote;
              }
              if(isset($filter)){ // Po odoslaní filtra v hlavičke dokumentu sa zobrazia údaje zhodné s hľadanou hodnotou v názve
                $title = strtolower($title);  // $title -> lowercase, teda predtým taktiež $filter -> lowercase,
                                              // aby pri porovnaní hodnôt nezáležalo na veľkých a malých písmenkách
                if($filter == $title && $calendar == False){
                  $counter += 1;
                  $classCounter = $classCounter . $counter; // Classy, aby bolo možné jednotlivým výpisom na stránke, priradil onClick action cez JS
                  $popUpCounter = $popUpCounter . $counter;
                  $closeCounter = $closeCounter . $counter;
                  echo $addNote;
                }
              }
            }
            if ($page > 1) {  // Stránkovanie -> leftArrow / čísla / rightArrow
              $prevlink = '<div class="pages"><a class="leftArrow" href="?page=1">&laquo;</a> <a class="leftArrow" href="?page='.($page-1).'">&lsaquo;</a>';
            } else {
              $prevlink = "<div class='pages'>&laquo; &lsaquo;";
            }
            
            $links = "";
            for ($i=0; $i < $pages; $i++) {
              if ($i == $page-1 ) {
                $links .= " ".($i+1)." ";
              } else {
                $links .= " <a class='betweenArrow' href=\"?page=".($i+1)."\">".($i+1)."</a> ";
              }
            }
            
            if ($page < $pages) {
              $nextlink = '<a class="rightArrow" href="?page='.($page+1).'">&rsaquo;</a> <a class="rightArrow" href="?page='.$pages.'">&raquo;</a></div>';
            } else {
              $nextlink = "&rsaquo; &raquo; </div>";
            }
            echo '<li class="number">' . $prevlink, $links, $nextlink . '</li>'; 
          }
          else{ // Chybová hláška, ak neexistujú záznamy pre daný filter
            echo '<li class="dbError"> Neexistujú poznámky. </li>';
          }
        }
        else{ // Chybová hláška, ak nefunguje databáza
          echo '<li class="dbError">' . $data . '</li>';
        }
        ?>
      </ul>
    </nav>
  </div>
  <div id="sendToJS" class="invisible">
    <?php // Neviditeľné na wesbstránke -> $counter posiela do popUpWindow.js,
      $output = $counter; // aby bolo možné zistiť počet vypísaných údajov na stránke
      echo htmlspecialchars($output);
    ?>
  </div>
  <script src="javascript/calendar.js"></script>
  <script src="javascript/popUpWindow.js"></script>
</article>
<?php
  include('includes/_footer.php');  // Zalinkovanie pätičky dokumentu
?>
