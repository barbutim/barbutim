<?php
    if($mainTitle != 'Prihlásenie'){    // Ak je načítaná iná stránka ako login.php, skontroluj, či je užívateľ prihlásený
        include('includes/_loginCheck.php');    // POZOR ! -> aby validator.w3.org vedel skontrolovať všetky podstránky, je nutné túto časť kódu dočasne vymazať
    }
    // Štýly
    $styleHeader1 = '<link rel="stylesheet" href="static/header.css">';
    $styleHeader2 = '<link rel="stylesheet" href="static/header2.css">';
    $styleFooter1 = '<link rel="stylesheet" href="static/footer.css">';
    $styleFooter2 = '<link rel="stylesheet" href="static/footer2.css">';
    $styleMain1 = '<link rel="stylesheet" href="static/main.css">';
    $styleMain2 = '<link rel="stylesheet" href="static/main2.css">';
    $styleForm1 = '<link rel="stylesheet" href="static/form.css">';
    $styleForm2 = '<link rel="stylesheet" href="static/form2.css">';

    $style = isset($_COOKIE['skin']) ? $_COOKIE['skin'] : 1;
    
    if(isset($_POST['skin'])){
        $style = $_POST['skin'];
        setcookie('skin', $style, time()+3600, '/~barbutim', 'wa.toad.cz');
    }
    // Ak je $style == 1, použí štýly č.1, ak $style == 2, použí štýly č.2
    $selectedSkinHeader = $style == 1 ? $styleHeader1 : $styleHeader2;
    $selectedSkinFooter = $style == 1 ? $styleFooter1 : $styleFooter2;
    $selectedSkinMain = $style == 1 ? $styleMain1 : $styleMain2;
    $selectedSkinForm = $style == 1 ? $styleForm1 : $styleForm2;
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><?php echo $mainTitle; ?></title>    <!-- Príjmanie $mainTitle z iných podstránok -->
    <link rel="stylesheet" href="https://maxst.icons8.com/vue-static/landings/line-awesome/line-awesome/1.3.0/css/line-awesome.min.css" />
    <?php   // Linkovanie štýlov
        echo $selectedSkinHeader;
        echo $selectedSkinFooter;

        if($mainTitle == 'Kalendár'){
            echo $selectedSkinMain;
        }

        else{
            echo $selectedSkinForm;
            echo '<script src="javascript/calendarReset.js"></script>';
        }
    ?>
    <script src="jquery/jquery-3.5.1.min.js"></script>  <!-- Jquery skript -->
</head>
<body>
    <header>    
        <?php if($mainTitle != 'Prihlásenie'){ ?>   <!-- Ak je načítaná iná stránka ako login.php, zobraz celú hlavičku -->
        <a href="main.php" class="title"><p>Online</p><p class="bold">kalendár</p></a>
        <nav>
            <ul>
                <li>
                    <form method="POST">    <!-- Filter podľa názvu poznámky -->
                        <label class="nameFilter">
                            <input class="filter" type="text" name="filter" pattern="[A-Za-z0-9\s]+">
                        </label>
                        <input class="send" type = "submit" value="<?php if($mainTitle == 'Poznámka') echo 'Prejdi na kalendár'; else{ if(isset($alert) && $alert) echo 'Rovnaký názov!'; else echo 'Vyhľadať názov';}?>" name = "searchInDb">
                    </form>
                </li>
                <li class="logout"><a class="button" href="includes/_logout.php">Odhlásiť sa</a></li>
                <li>
                    <form method="POST">    <!-- Dve zobrazenia stránky ("blue","red") -->
                        <label>
                            <input class="inv" value="1" type="submit" name="skin"/>
                            <span class="radioButton">
                                <span class="wrap">
                                    <span class="las la-file-powerpoint blue"></span>
                                    <span class="blue iconName">Blue</span>
                                </span>
                            </span>
                        </label>
                        <label>
                            <input class="inv" value="2" type="submit" name="skin"/>
                            <span class="radioButton">
                                <span class="wrap">
                                    <span class="las la-file-powerpoint red"></span>
                                    <span class="red iconName">Red</span>
                                </span>
                            </span>
                        </label>
                    </form>
                </li>
            </ul>
        </nav>
        <?php } else{ ?>    <!-- Ak je načítaná stránka login.php, zobraz iba "title" -->
            <a class="title"><p>Online </p><p class="bold">kalendár</p></a>
        <?php } ?>
    </header>