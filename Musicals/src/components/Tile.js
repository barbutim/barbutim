import React, {Component} from "react";
import {Col, Row} from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css'
import { FaRegHeart } from "react-icons/fa"

class Tile extends Component{

    constructor(props) {
        super(props);
        this.state = {
            data: []
        }
    }
  
    componentDidMount(){}
            
    render() {

        const data = this.props.data

        return (
            <Col key={data.EventId}>

                <div className="product-photo">
                    <img src={data.SmallImageUrl.toString()} alt=""/>
                </div>
                <Row>
                    <Col xs={10} className={"home__card__text"}><span>{data.Name}</span></Col>
                    <Col xs={2}><p className={"home__card__icon"}><FaRegHeart className={"fa-button"}/></p><p className={"home__card__icon"}>58</p></Col>
                </Row>

            </Col>
        )
    }
    
}

export default Tile;