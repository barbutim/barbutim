<?php
    session_start();    //príjmanie AJAX requestu z calendar.js -> ukladanie hodnôt do $_SESSION

    if(isset($_POST['dayVal'])) {
        $_SESSION['dayVal'] = $_POST['dayVal'];
    }
    
    if(isset($_POST['monthVal'])) {
        $_SESSION['monthVal'] = $_POST['monthVal'];
    }
    
    if(isset($_POST['yearVal'])) {
        $_SESSION['yearVal'] = $_POST['yearVal'];
    }
?>