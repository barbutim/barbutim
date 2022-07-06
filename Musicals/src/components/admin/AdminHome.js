import React, { Component, useState } from 'react'
import { Link, useNavigate } from "react-router-dom"
import {Container, Row, Button, Card, Alert} from "react-bootstrap"
import 'bootstrap/dist/css/bootstrap.min.css'
import '../../App.css'
import { useAuth } from "../../contexts/AuthContext"
import { getDbCollection } from "../../services/database"
import Tile from '../Tile'
export default class AdminHome extends Component {

    constructor(props) {
        super(props);
        this.state = {
            data: []

        }
    }

    async componentDidMount() {
        const a = await getDbCollection("muzikals");
        this.setState({
            data: a
        });
    }

    render() {

        var { data } = this.state;

        if(data.length === 0) {
            return <div>Loading...</div>
        } else {
            return (
              <div>
                <AdminTab />
                <div className='mainHomeContainer'>
                    <div className='container-for-search-items'>
                        <div><h4>Panel</h4></div>
                        <div><hr className={"m-2 mt-1 mb-1"}/></div>
                        <div className={"d-grid gap-2 p-3 pt-1"}><Button size='lg'>Refresh By Api</Button></div>
                        <div className={"d-grid gap-2 p-3 pt-1"}><Link to = '/muzikal/edit'>New Muzikal</Link></div>
                        <div><Link to = '/' className={"text-decoration-none text-black"}>Home</Link></div>
                    </div>
                    <Container>
                        <Row>
                            {data.map(item => (
                                <Link key={item.id} className={"home__card col-lg-4 col-md-6 col-xl-3 col-sm-6 col-12"} to={`/muzikal/edit?id=${item.id}`}>
                                    <Tile data = {item.data}/>
                                </Link>
                            ))}
                        </Row>
                    </Container>
                </div>
              </div>
            )
        }
    }   
}

function AdminTab() {
  const [error, setError] = useState("")
  const { currentUser, logout } = useAuth()
  const navigate = useNavigate()

  async function handleLogout() {
    setError("")

    try {
      await logout()
      navigate("/login")
    } catch {
      setError("Failed to log out")
    }
  }

  return (
    <>
      <Card>
        <Card.Body>
          <h2 className="text-center mb-4">Profile</h2>
          {error && <Alert variant="danger">{error}</Alert>}
          <strong>Email:</strong> {currentUser.email}
        </Card.Body>
      </Card>
      <div className="w-100 text-center mt-2">
        <Button variant="link" onClick={handleLogout}>
          Log Out
        </Button>
      </div>
    </>
  )
}