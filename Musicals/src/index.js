import React from 'react'
import { createRoot } from 'react-dom/client'
import Navbar from 'react-bootstrap/Navbar'
import Container from 'react-bootstrap/Container';
import "bootstrap/dist/css/bootstrap.css"
import App from './App';

const container = document.getElementById('root')
const root = createRoot(container);

root.render(
  <div>
    <Navbar sticky="top" bg="light" variant="light">
    <Container>
      <Navbar.Brand>
        <img
          alt="logo"
          src="/logo512.png"
          width="30"
          height="30"
          className="d-inline-block align-top"
        />{' '}
          TicketsForTonight
      </Navbar.Brand>
    </Container>
  </Navbar>
    <React.StrictMode>
      <App />
    </React.StrictMode>
  </div>
);
