import React, { Component } from 'react';
import { Button, Col, Container, Row } from "react-bootstrap";
import { FaArrowLeft, FaRegHeart } from "react-icons/fa";
import { Link } from "react-router-dom";
import { firstLayout } from '../layouts/FirstLayout';
import { getDbCollection } from '../services/database';

class Muzikal extends Component {

    constructor(props) {
        super(props);
        this.state = {
            muzikalId: 0,
            data: [],
            isLoaded: false,
            layout: [],
            resp: [],
            isClicked: false,
            likeCount: 60
        }

    }

    async componentDidMount() {
        const id = new URLSearchParams(window.location.search).get('id');
        const docs = await getDbCollection("muzikals");
        
        docs.forEach(doc => {
            if (doc.id === id) {
                this.setState({
                    muzikalId: id,
                    data: doc,
                    layout: firstLayout
                });
            }
        })
        

        // const fetchData = async () => {
        //     const respData = await getMuzikalById(id);

        //     this.setState({
        //         data: respData.shift(),
        //         isLoaded: true,
        //         resp: []
        //     })

        // }
        // fetchData();
    }

    render() {

        let {data, likeCount} = this.state

        function  click(){

            //this.setState({
                //likeCount: count
            //})
        }

        if (data.length === 0) {
            return <p>Loading...</p>
        } else {

            let muzikal = data.data;
            let startDate = new Date(muzikal.StartDate);
            let endDate = new Date(muzikal.EndDate);
            const monthes = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
            const daysOfWeek = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
            
            return (

                <Container fluid className={"musical__card"}>
                    <Link to = '/' className={"musical__backButton"}><FaArrowLeft size={"28"} color={"rgba(0,0,0, 0.5)"}/></Link>
                    <Row>
                        <Row className={"musical__header"}>
                            <Col xl={{span: 7, offset: 2}} lg={{span: 7, offset:2}} md={12} sm={12} xs={12}>
                                <Row className={"musical__title"}>
                                    <Col xl={8} lg={8} md={12} sm={12} xs={12}><img src={muzikal.SmallImageUrl.toString()} alt=""/></Col>
                                    <Col xl={4} lg={4} md={12} sm={12} xs={12}><div><h2>{muzikal.Name}</h2><h2><FaRegHeart className={"fa-button"} onClick={click}/> {likeCount} </h2></div></Col>
                                </Row>
                            </Col> 

                            <Col xl={2} lg={2} md={12} sm={12} xs={12} className={"musical__buy"}>
                                <div>
                                    <div><p>From {muzikal.CurrentPrice} &#163; </p></div>
                                    <div><Button size="lg" variant="success">Buy ticket</Button></div>
                                </div>

                            </Col>
                        </Row>
                        <Col xl={{ span: 10, offset:1 }} lg={12} className={"musical__description"} dangerouslySetInnerHTML={{__html: muzikal.Description}}></Col>



                        <Col lg={{span: 4, offset: 2}} md={{span: 4, offset: 2}} xs={12} xl={{span: 4, offset: 2}} className={"text-right musical__date"}>
                            <h2 className={"text-center"}>Date & time</h2>
                            {muzikal.EndDate !== "" ? <p>Until: {endDate.getDate()} {monthes[endDate.getMonth()]}</p> :
                                <p>Actual</p>}
                            <p>{daysOfWeek[startDate.getDay()]} {startDate.getDate()} {monthes[startDate.getMonth()]} {startDate.getFullYear()} - {daysOfWeek[endDate.getDay()]} {endDate.getDate()} {monthes[endDate.getMonth()]} {endDate.getFullYear()}</p>
                            {muzikal.RunningTime !== "" ? <p>Runtime: {muzikal.RunningTime}</p> : ""}
                        </Col>
                        <Col lg={4} md={4} xs={12} xl={4} className={"text-center musical__presented"}>
                            <h2>Minimal age</h2>
                            <p>{muzikal.MinimumAge}</p>

                        </Col>
                    </Row>
                </Container>
            )
        }
    }

}

export default Muzikal;