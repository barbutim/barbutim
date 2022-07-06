import { initializeApp } from 'firebase/app'
import { getAuth } from 'firebase/auth'

import firebase from 'firebase/compat/app';
import 'firebase/compat/firestore';


const firebaseConfig = {
  apiKey: "AIzaSyDutDGE-iSSACZmMPMepx-z6gyle8WHgUw",
  authDomain: "rsp-web-muzikal.firebaseapp.com",
  databaseURL: "https://rsp-web-muzikal-default-rtdb.europe-west1.firebasedatabase.app",
  projectId: "rsp-web-muzikal",
  storageBucket: "rsp-web-muzikal.appspot.com",
  messagingSenderId: "484554148037",
  appId: "1:484554148037:web:83decb557c88eacd7548ac",
  measurementId: "G-SLSFJDT4G8"
};

const app = initializeApp(firebaseConfig);

export const auth = getAuth(app);

const firebaseApp = firebase.initializeApp(firebaseConfig);
export const db = firebaseApp.firestore();

