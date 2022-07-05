import React from "react";
import Card from "react-bootstrap/Card";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Button, Row, Col } from "react-bootstrap";
import { Link } from "react-router-dom";
import Highlighter from "react-highlight-words";

/**
 * Job offers default size
 */
class TripMedium extends React.Component {
    render() {
        const reviewStars = [];
        for (let i = 0; i < 5; i++) {
            if (i + 1 <= this.props.trip.rating) {
                reviewStars.push(<FontAwesomeIcon icon="star" />);
            } else if (
                i - this.props.trip.rating < 0 &&
                i - this.props.trip.rating > -1
            ) {
                reviewStars.push(<FontAwesomeIcon icon="star-half" />);
            } else {
                reviewStars.push(<FontAwesomeIcon icon={["far", "star"]} />);
            }
        }

        let numberOfDates = 0;
        this.props.trip.sessions.forEach(() => {
            numberOfDates++;
        });

        return (
            <Link to={"/trips/" + this.props.trip.short_name}>
                <Card className="mb-3 searchTrip">
                    <Card.Body className="d-flex">
                        <Col xs={5}>
                            <div className="image-card">
                                <Card.Img
                                    variant="top"
                                    src="https://tesla-cdn.thron.com/delivery/public/image/tesla/21f55e82-6908-4570-9396-d76972959944/bvlatuR/std/2880x1606/Giga-Berlin-Main-Hero-Desktop"
                                />
                            </div>
                        </Col>
                        <Col xs={7}>
                            <h4 className="ml-3">
                                <Highlighter
                                    highlightClassName="textHighlight"
                                    searchWords={
                                        this.props.highlightWords
                                            ? this.props.highlightWords
                                            : []
                                    }
                                    autoEscape={true}
                                    textToHighlight={this.props.trip.name}
                                />
                            </h4>

                            <Highlighter
                                highlightClassName="textHighlight"
                                searchWords={
                                    this.props.highlightWords
                                        ? this.props.highlightWords
                                        : []
                                }
                                autoEscape={true}
                                textToHighlight={this.props.trip.description}
                            />
                            <Row>
                                <Col className="d-flex flex-column date_stars">
                                    <Row>{reviewStars}</Row>
                                </Col>
                                <Col className="text price">
                                    <span>{this.props.trip.salary} Kč/Hod</span>
                                </Col>
                            </Row>
                        </Col>
                    </Card.Body>
                </Card>
            </Link>
        );
    }
}

export default TripMedium;
