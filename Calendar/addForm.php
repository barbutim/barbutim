<?php
    include('includes/_fileDB.php');    // Zalinkovanie údajov z databázy a funkcií
    include('includes/_validate.php');  // Zalinkovanie validácie formuláru
    session_start();

    if(isset($_POST['searchInDb'])){    // Ak bolo stlačené tlačidlo pre vyhľadanie názvu v hlavičke dokumentu...
        header('Location: main.php');   // Hodnota nebude filtrovaná, ale -> prechod na main.php
    }

    if(isset($_POST['editNote'])){  // Ak bolo na main.php stlačené tlačidlo pre úpravu poznámky
        $id = $_POST['editNote'];
        $note = findNote($data, $id);   // Podľa ID nájdi poznámku v databáze
        $dateValue = $note['date'];
        $timeValue = $note['time'];
        $titleValue = $note['title'];
        $textValue = $note['text'];
    }

    else{
        $dateValue = '';
        $timeValue = '';
        $titleValue = '';
        $textValue = '';
        // Príjmanie hodnôt z calendar.js -> _getValuesFromJS.php -> main.php -> addForm.php -> $_SESSION
        if(isset($_SESSION['dayVal']) && isset($_SESSION['monthVal']) && isset($_SESSION['yearVal'])){
            $dayVal = $_SESSION['dayVal'];
            $monthVal = $_SESSION['monthVal'];
            $yearVal = $_SESSION['yearVal'];
            $dateValue = $yearVal . '-' . $monthVal . '-' . $dayVal;    // Prednastavenie dátumu, podľa dátumu zakliknutého v kalendáry na main.php
        }
    }
    // Ak bolo stlačené tlačidlo pre upravenie/pridanie poznámky na addForm.php
    if(isset($_POST['addNote']) || isset($_POST['updateNote'])){
        $isSent = count($_POST) > 0;

        if($isSent){    // Ak boli vyplnené údaje na addForm.php
            $dateValue = $_POST['date'];
            $timeValue = $_POST['time'];
            $titleValue = $_POST['title'];
            $textValue = $_POST['text'];
            $validDate = validateDate($dateValue);  // Validovanie hodnôt cez _validate.php
            $validTime = validateTime($timeValue);
            $validTitle = validateTitle($titleValue);
            $validText = validateText($textValue);  
            $valid = $validDate && $validTime && $validTitle && $validText;
            if($data != 'Databáza nie je funkčná!'){    // Ak nie je funkčná (zalinkovaná) databáza -> presmerovanie na main.php bez zápisu údajov
                if($valid){ // Ak sú všetky hodnoty validné...
                    if(isset($_POST['addNote'])){   // Ak bolo stlačené tlačidlo pre pridanie poznámky
                        $newNote = array(   // Údaje z formulára sa uložia do Array listu
                            'id' => uniqid(),
                            'date' => $_POST['date'],
                            'time' => $_POST['time'],
                            'title' => $_POST['title'],
                            'text' => $_POST['text']
                        );
                        storeData($data, $newNote); // Následne sa uložia do databázy -> presmerovanie na main.php
                        header("Location: main.php");
                    }

                    if(isset($_POST['updateNote'])){    // Ak bolo stlačené tlačidlo pre upravenie poznámky
                        deleteNote($data, $_POST['id']);    // Pôvodná poznámka sa vymaže
                        $data = file_get_contents(DBFILE);
                        $data = json_decode($data, JSON_OBJECT_AS_ARRAY);
                        $newNote = array(   // Údaje z formulára sa uložia do Array listu
                            'id' => uniqid(),
                            'date' => $_POST['date'],
                            'time' => $_POST['time'],
                            'title' => $_POST['title'],
                            'text' => $_POST['text']
                        );
                        storeData($data, $newNote); // Následne sa uložia do databázy -> presmerovanie na main.php
                        header("Location: main.php");
                    }
                }
                else{
                    if($validDate == False){    // Chybové hlášky
                        $error = 'Zle zadaný dátum!';
                    }
    
                    else if($validTime == False){
                        $error = 'Zle zadaný čas!';
                    }
    
                    else if($validTitle == False){
                        $error = 'Názov musí obsahovať 5-30 znakov!';
                    }
    
                    else if($validText == False){
                        $error = 'Text môže obsahovať maximum 150 znakov!';
                    }
                }
            }
            else{
                header("Location: main.php");
            }
        }
    }
    $mainTitle = 'Poznámka';
    include('includes/_header.php');    // Zalinkovanie hlavičky dokumentu
?>
<script src="javascript/form.js"></script>
<article>
    <form method="POST" id="formular">  <!-- Schovaná časť formulár, kde sa ukladá ID poznámky, -->
        <?php   // pri úprave poznámky z main.php alebo po chybnom odoslaní formuláru
            if(isset($id)){
                echo '<input type="hidden" name="id" value="'.$id.'">';
            }

            if(isset($_POST['id'])){
                echo '<input type="hidden" name="id" value="'.$_POST['id'].'">';
            }
        ?>
        <h2>Zadaj údaje</h2>
        <div>   <!-- Viditeľná časť formuláru -->
            <label>
                <span class="state">Dátum:</span>
                <span class="warning req">required</span>
                <input id="date" type="date" name="date" value="<?php echo htmlspecialchars($dateValue)?>" required >
            </label>
        </div><div>
            <label>
                <span class="state">Čas:</span>
                <span class="warning req">required</span>
                <input id="time" class="test" type="time" name="time" value="<?php echo htmlspecialchars($timeValue)?>" required >
            </label>
        </div><div>
            <label>
                <span class="state">Názov:</span>
                <span class="warning req">required</span>
                <input id="title" type="text" name="title" placeholder="Zadaj názov poznámky..." value="<?php echo htmlspecialchars($titleValue)?>" required pattern="[A-Za-z0-9\s]+" >
            </label>
        </div><div>
            <label>
                <span class="state">Text poznámky:</span>
                <textarea id="text" name="text" placeholder="Zadaj text poznámky..." ><?php echo htmlspecialchars($textValue)?></textarea>
            </label>
        </div>
        <?php 
            echo (isset($id)) || (isset($_POST['id'])) ? '<input class="sendButton" type="submit" id="addNote" name="updateNote" value="Upraviť">':''; // Ak je presmerovanie z addForm.php po kliknutí na úpravu poznámky
            echo (empty($id)) && (empty($_POST['id'])) ? '<input class="sendButton" type="submit" id="addNote" name="addNote" value="Vytvoriť">':'';    // Ak je presmerovanie z addForm.php po kliknutí na vytvorenie poznámky
            echo isset($error) ? "<p class='warning'>".$error."</p>" : ''; // Zobrazenie chybových hlášok
        ?>
    </form>
</article>
<?php
    unset($_SESSION['error']);
    include('includes/_footer.php');    // Zalinkovanie pätičky dokumentu
?>