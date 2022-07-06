import AdminHome from "../components/admin/AdminHome";
import {Link} from "react-router-dom";
import React from "react";
import AdminLayout from "../components/admin/AdminLayout";
import AdminMuzikals from "../components/admin/AdminMuzikals";
import LoginAdmin from "../components/admin/LoginAdmin";
import registerAdmin from "../components/admin/RegisterAdmin";

test('adminHome works', () => {
    const result = (
        <div>
            AdminHome

            <Link to = "/">Log out</Link>
            <Link to = "/admin/layout">Layouts</Link>
            <Link to = "/admin/muzikals">Musicals</Link>
        </div>
    );
    expect(AdminHome()).toStrictEqual(result);
});

test('adminLayout works', () =>{
    const result = (
        <div>
            AdminLayout
        </div>
    );
    expect(AdminLayout()).toStrictEqual(result);
});

test('adminMuzikals works', ()=>{
    const result = (
        <div>
            AdminMusicals
        </div>
    );
    expect(AdminMuzikals()).toStrictEqual(result);
});

test('loginAdmin works', ()=>{
   const result = (
       <div>
           LoginAdmin

           <Link to = "/admin">Log in</Link>
           <Link to = "/reg">Register</Link>
       </div>
   );
   expect(LoginAdmin()).toStrictEqual(result);
});

test('registerAdmin works', ()=>{
    const result = (
        <div>
            RegisterAdmin

            <Link to = "/admin">Register</Link>
        </div>
    );
    expect(registerAdmin()).toStrictEqual(result);
});