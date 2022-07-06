import '../App.css';
import React, {Component, useState} from 'react'
import {Link} from 'react-router-dom'
import Tile from "./Tile"
import {Container, Row, Button} from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css';
import { getDbCollection } from '../services/database';
import axios from "axios";
import data from "bootstrap/js/src/dom/data";

export default class Home extends Component {

    constructor(props){
        super(props);
        this.state = {
            data: [],
            constData: [],
            startPrice: 0,
            endPrice: 5000,
            findDate: Date.now(),
            nowDate: Date.now()
        }
        this.changeStartPrice = this.changeStartPrice.bind(this);
        this.changeEndPrice = this.changeEndPrice.bind(this);
        this.changeDate = this.changeDate.bind(this);
    }

    changeStartPrice(event) {
        this.setState({startPrice: event.target.value});
    }

    changeEndPrice(event) {
        this.setState({endPrice: event.target.value});
    }

    changeDate(event) {
        this.setState({findDate: event.target.value});
    }



    async componentDidMount() {
        const a = await getDbCollection("muzikals");
        this.setState({
            data: a,
            constData: a
        });
    }

            
    render() {
        function checkImg(path) {
            axios.get(path)
                .then(() => {
                    return true;
                })
                .catch(() => {
                    return false;
                });
        }

        console.log(this.state.constData);
        const currentDate = new Date();
        const now = currentDate.toLocaleString();

        if(this.state.data.length === 0) {
            return (
                <div className='mainHomeContainer'>
                    <div className='container-for-search-items'>
                        <div>
                        </div>
                        <div><h4>Date</h4></div>
                        <div><hr className={"m-2 mt-1 mb-1"}/></div>
                        <form>
                            <div className="form-group">
                                <div className="input-group date mb-1 p-2">
                                    <input type="date" className="form-control date_musical" data-date-format="mm/dd/yyyy" value={this.state.findDate} onChange={this.changeDate}/>
                                    <span className="input-group-addon">
                                        <span className="glyphicon glyphicon-calendar"></span>
                                    </span>
                                </div>
                                <div className={"d-grid gap-2 p-3 pt-1"}><Button variant='success' size='md' onClick={() => {this.setState({data: this.state.constData.filter(item => item.data.StartDate<=this.state.findDate && item.data.EndDate>=this.state.findDate)})}}>Filter</Button></div>
                            </div>
                        </form>
                        <div><h4>Price</h4></div>
                        <div><hr className={"m-2 mt-1 mb-1"}/></div>
                        <form>
                            <div className="input-group mb-1 p-2">
                                <div className="input-group-prepend">
                                    <span className="input-group-text">From:</span>
                                </div>
                                <input type="number" className="form-control" value={this.state.startPrice} onChange={this.changeStartPrice}/>
                            </div>
                            <div className="input-group mb-1 p-2">
                                <div className="input-group-prepend">
                                    <span className="input-group-text">To: </span>
                                </div>
                                <input type="number" className="form-control" value={this.state.endPrice} onChange={this.changeEndPrice}/>
                            </div>
                            <div className={"d-grid gap-2 p-3 pt-1"}><Button size='md' variant='success' onClick={() => {this.setState({data: this.state.constData.filter(item => item.data.CurrentPrice>=this.state.startPrice && item.data.CurrentPrice<=this.state.endPrice)})}}>Filter</Button></div>
                        </form>
                        <div className={"text-center"}><Link to = '/login' className={"text-decoration-none text-black"}><Button>Admin</Button></Link></div>
                    </div>
                    <Container>
                        <Row>
                            <span className="homeLoading">Loading...</span>
                        </Row>
                    </Container>
                </div>
            )
        }
        else {
            return (
                <div className='mainHomeContainer'>
                    <div className='container-for-search-items'>
                        <div>
                        </div>
                        <div><h4>Date</h4></div>
                        <div><hr className={"m-2 mt-1 mb-1"}/></div>
                        <form>
                            <div className="form-group">
                                <div className="input-group date mb-1 p-2">
                                    <input type="date" className="form-control date_musical" data-date-format="mm/dd/yyyy" value={this.state.findDate} onChange={this.changeDate}/>
                                    <span className="input-group-addon">
                                        <span className="glyphicon glyphicon-calendar"></span>
                                    </span>
                                </div>
                                <div className={"d-grid gap-2 p-3 pt-1"}><Button variant='success' size='md' onClick={() => {this.setState({data: this.state.constData.filter(item => item.data.StartDate<=this.state.findDate && item.data.EndDate>=this.state.findDate)})}}>Filter</Button></div>
                            </div>
                        </form>
                        <div><h4>Price</h4></div>
                        <div><hr className={"m-2 mt-1 mb-1"}/></div>
                        <form>
                            <div className="input-group mb-1 p-2">
                                <div className="input-group-prepend">
                                    <span className="input-group-text">From:</span>
                                </div>
                                <input type="number" className="form-control" value={this.state.startPrice} onChange={this.changeStartPrice}/>
                            </div>
                            <div className="input-group mb-1 p-2">
                                <div className="input-group-prepend">
                                    <span className="input-group-text">To: </span>
                                </div>
                                <input type="number" className="form-control" value={this.state.endPrice} onChange={this.changeEndPrice}/>
                            </div>
                            <div className={"d-grid gap-2 p-3 pt-1"}><Button size='md' variant='success' onClick={() => {this.setState({data: this.state.constData.filter(item => item.data.CurrentPrice>=this.state.startPrice && item.data.CurrentPrice<=this.state.endPrice)})}}>Filter</Button></div>
                        </form>
                        <div className={"text-center"}><Link to = '/login' className={"text-decoration-none text-black"}><Button>Admin</Button></Link></div>
                    </div>
                    <Container>
                        <Row>
                            {this.state.data./*filter(item => item.data.EndDate>=this.state.findDate).*/map(item => (
                                <Link key={item.id} className={"home__card col-lg-4 col-md-6 col-xl-3 col-sm-6 col-12"} to={`/muzikal?id=${item.id}`}>
                                    <Tile data = {item.data}/>
                                </Link>
                            ))}
                        </Row>
                    </Container>

                </div>
                
            )
        }
    }
}
