const initialNotes = {
    notes: [
        { checked: true, message: 'Some stuff I need to do from school' },
        { checked: false, message: 'Hang out with friend in the evening' },
        { checked: false, message: 'Watching penguins in the Zoo' },
        { checked: true, message: 'Movies in cinema that I liked as young' }
    ]
};

class Notes {
    constructor(initNotes, notesEl) {
        this.notes = initNotes;
        this.ulEl = notesEl;
        this.detail = "";
        //this.clearLocalStorage();
        this.getFromLocalStorage();
        //this.getFromConstant();
        this.createHtml();
        this.changeBackground();
        this.setupListeners();
        this.updateDate();
    }

    /**
     * creates elements with actual notes and pushes them into HTML, to make them visible for the user
     */
    createHtml() {
        this.ulEl.innerHTML = '';
        let i = 0;
        for(let note of this.notes) {
            const labelEl = document.createElement('label');
            labelEl.classList.add('note');
            labelEl.textContent = note.message;
            labelEl.id = 'remove_text';

            const divEl = document.createElement('div');
            divEl.classList.add('note_field');
            divEl.appendChild(labelEl);

            const imgEl = document.createElement('img');
            imgEl.classList.add('remove_image');
            imgEl.src = 'remove.jpg';
            imgEl.id = 'remove_button';

            const buttonEl = document.createElement('button');
            buttonEl.classList.add('remove_button');
            buttonEl.appendChild(imgEl);

            const inputEl = document.createElement('input');
            inputEl.classList.add('active_note');
            inputEl.type = 'checkbox';
            inputEl.checked = note.checked;
            inputEl.id = 'remove_checkbox';

            const liEl = document.createElement('li');
            liEl.classList.add('current_note');
            liEl.appendChild(inputEl);
            liEl.appendChild(divEl);
            liEl.appendChild(buttonEl);

            this.ulEl.appendChild(liEl);
            i++;
        }
        const removes = document.querySelectorAll('#remove_button');
        for(let remove of removes) {
            remove.addEventListener('click', () => this.removeNote(event.target));
        }

        const checkboxes = document.querySelectorAll('.active_note');
        for(let checkbox of checkboxes) {
            checkbox.addEventListener('click', () => this.checkedState(event.target));
        }

        const noteDetails = document.querySelectorAll('.note_field');
        for(let noteDetail of noteDetails) {
            noteDetail.addEventListener('click', () => this.showDetail(event.target));
        }

        const counter = document.querySelector('.notes_number');
        counter.textContent = "Number of notes: " + this.notes.length;
    }

    /**
     * removes child elements of ul element
     */
    removeHtml() {
        this.ulEl.innerHTML = '';
    }

    /**
     * action when user adds note using enter key
     */
    addNoteWithEnter() {
        if(event.keyCode === 13 && document.getElementById('add_text').value !== '') {
            this.addNote();
        }
    }

    /**
     * action when user adds note using button next to input
     */
    addNoteWithButton() {
        if(document.getElementById('add_text').value !== '') {
            this.addNote();
        }
    }

    /**
     * pushes input into 'database', updates UI for user, plays music and pushes 'database' values into LocalStorage
     */
    addNote() {
        const checkbox = document.getElementById('add_checkbox').checked;
        const entry = document.getElementById('add_text').value;

        const newNote = { checked: checkbox, message: entry }
        this.notes.push(newNote);

        this.createHtml();
        document.getElementById('add_text').value = '';
        this.changeBackground();

        const sound_add = new Audio("add.wav");
        sound_add.play();
        this.setLocalStorage();
    }

    /**
     * removes element from 'database', updates UI for user, plays music and pushes 'database' values into LocalStorage
     * if user has opened a detail window of the same note he deletes, detail window disappears and smile appears
     * @param element is event.target
     */
    removeNote(element) {
        this.removeHtml();
        for(let i = 0; i < this.notes.length; i++) {
            if(this.notes[i].message === element.parentElement.parentElement.childNodes[1].childNodes[0].textContent) {
                if(this.notes[i].message === this.detail) {
                    this.hideDetail();
                    this.showSmile();
                    setTimeout(() => this.hideSmile(), 1500);
                }
                this.notes.splice(i,1);
            }
        }

        this.createHtml();
        this.changeBackground();
        const sound_remove = new Audio("remove.wav");
        sound_remove.play();
        this.setLocalStorage();
    }

    /**
     * updates background image height based on number of notes there is
     */
    changeBackground() {
        const image = document.querySelector('.block_image');
        image.style.height = initialNotes.notes.length * 50 + "px";
    }

    /**
     * updates checked state of note in the 'database'
     * @param element is event.target
     */
    checkedState(element) {
        for(let note of this.notes) {
            if(note.message === element.parentNode.childNodes[1].childNodes[0].textContent) {
                if(note.checked) {
                    note.checked = false;
                }
                else {
                    note.checked = true;
                }
                break;
            }
        }
    }

    /**
     * shows detail window of the exact note the user just clicked on
     * @param element is event.target
     */
    showDetail(element) {
        const checked = document.querySelector('.detail_checked');
        const message = document.querySelector('.detail_message');
        this.detail = element.textContent;
        for(let note of this.notes) {
            if(note.message === this.detail) {
                let state = "";
                if(note.checked) {
                    state = "Solved";
                }
                else {
                    state = "Not Solved Yet";
                }
                checked.textContent = "State: " + state;
                message.textContent = "Message: " + note.message;
                break;
            }
        }
        document.querySelector('.detail').style.display = "block";
    }

    /**
     * hide detail window of note
     */
    hideDetail() {
        this.detail = "";
        document.querySelector('.detail').style.display = "none";
    }

    /**
     * shows smile animation
     */
    showSmile() {
        document.querySelector('.smile').style.display = "block";
    }

    /**
     * hides smile animation
     */
    hideSmile() {
        document.querySelector('.smile').style.display = "none";
    }

    /**
     * clears LocalStorage
     */
    clearLocalStorage() {
        localStorage.clear();
    }

    /**
     * pushes 'database' into LocalStorage
     */
    setLocalStorage() {
        this.clearLocalStorage();
        for(let i = 0; i < this.notes.length; i++) {
            localStorage.setItem(i + '_checked', this.notes[i].checked);
            localStorage.setItem(i + '_message', this.notes[i].message);
        }
    }

    /**
     * gets data from LocalStorage into 'database'
     */
    getFromLocalStorage() {
        let i = 0;
        let checked;
        const checkbox_ = localStorage.getItem('0_checked');
        const entry_ = localStorage.getItem('0_message');

        if(checkbox_ === null || entry_ === null) {
            return;
        }
        this.notes.splice(0,this.notes.length);

        while(true) {
            let checkbox = localStorage.getItem(i + '_checked');
            let entry = localStorage.getItem(i + '_message');

            if(checkbox === null || entry === null) {
                break;
            }

            if(checkbox === 'true') {
                checked = true;
            }
            else {
                checked = false;
            }

            let newNote = { checked: checked, message: entry }
            this.notes.push(newNote);
            i++;
        }
    }

    /**
     * gets default 'database' data
     */
    getFromConstant() {
        this.notes = initialNotes.notes;
    }

    /**
     * sets up listeners of the page
     */
    setupListeners() {
        const removes = document.querySelectorAll('#remove_button');
        for(let remove of removes) {
            remove.addEventListener('click', () => this.removeNote(event.target));
        }
        const checkboxes = document.querySelectorAll('.active_note');
        for(let checkbox of checkboxes) {
            checkbox.addEventListener('click', () => this.checkedState(event.target));
        }
        const noteDetails = document.querySelectorAll('.note_field');
        for(let noteDetail of noteDetails) {
            noteDetail.addEventListener('click', () => this.showDetail(event.target));
        }

        document
            .getElementById('add_text')
            .addEventListener('keyup', () => this.addNoteWithEnter());
        document
            .getElementById('add_button')
            .addEventListener('click', () => this.addNoteWithButton());
        document
            .getElementById('hide_button')
            .addEventListener('click', () => this.hideDetail());
    }

    /**
     * updates date of the year
     */
    updateDate() {
        const dd = String(new Date().getDate());
        const mm = String(new Date().getMonth());
        const yyyy = String(new Date().getFullYear());

        const date = document.querySelector('.date');
        date.textContent = dd + "/" + mm + "/" + yyyy;
    }
}

let notes = new Notes(initialNotes.notes, document.querySelector('.note_list'));