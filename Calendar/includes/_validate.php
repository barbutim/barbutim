<?php   // Validácia pre addForm.php
    function validateDate($date){
        return strlen($date) == 10;
    }

    function validateTime($time){
        return strlen($time) == 5;

    }
    function validateTitle($title){
        return strlen($title) >= 5 && strlen($title) <= 30;
    }
    
    function validateText($text){
        return strlen($text) <= 150;
    }
?>