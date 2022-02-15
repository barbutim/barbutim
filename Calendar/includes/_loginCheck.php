<?php
    if(session_status() == PHP_SESSION_NONE){ // Ak ešte nebola vytvorená session -> vytvorí ju
        session_start();
    }
    // Ak je v $_SESSION['userID'] hodnota, zapíše ju do $signedIn
    $signedIn = isset($_SESSION['userID']) ? $_SESSION['userID'] : false;
    // Ak je $signedIn == False, užívateľ nie je prihlásený -> presmerovanie na login.php 
    if(!$signedIn){
        header('Location: login.php');
    }
?>