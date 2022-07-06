import React from 'react';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { AuthProvider } from "./contexts/AuthContext";
import './App.css';
import AdminHome from './components/admin/AdminHome';
import LoginAdmin from './components/admin/LoginAdmin';
import MuzikalEdit from './components/admin/MuzikalEdit';
import Home from './components/Home';
import Muzikal from './components/Muzikal';
import PrivateRoute from "./components/PrivateRoute";


export default function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/muzikal" element={<Muzikal />} />
          <Route path="/admin" element={<PrivateRoute />} >
            <Route path='/admin' element={<AdminHome />} />
          </Route>
          <Route path="/login" element={<LoginAdmin />} />
          <Route path="/muzikal/edit" element={<PrivateRoute />} >
            <Route path='/muzikal/edit' element={<MuzikalEdit />} />
          </Route>
        </Routes>
      </AuthProvider>
    </BrowserRouter>
  )
}
        