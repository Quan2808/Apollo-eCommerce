// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
import 'firebase/storage';
import { getStorage } from "firebase/storage";


// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
    apiKey: process.env.REACT_APP_API_KEY,
    authDomain: "apollo-915f8.firebaseapp.com",
    databaseURL: "https://apollo-915f8-default-rtdb.firebaseio.com",
    projectId: "apollo-915f8",
    storageBucket: "apollo-915f8.appspot.com",
    messagingSenderId: "734975645769",
    appId: "1:734975645769:web:8adedd99f4aa211bf24798",
    measurementId: "G-CEE7BS2518"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
export const firebaseStorage = getStorage(app);
export const analytics = getAnalytics(app);