<?php
    session_start();    // Vymazanie všetkých session
    $_SESSION = array();
    session_destroy();
    // Odhlásenie -> presmerovanie na login.php
    header('Location: ../login.php');
?>