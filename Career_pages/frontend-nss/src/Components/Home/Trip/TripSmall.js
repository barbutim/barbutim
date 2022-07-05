import React from "react";
import Card from "react-bootstrap/Card";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Button, Row, Col } from "react-bootstrap";
import { Link } from "react-router-dom";

/**
 * job offers small size
 */
class TripSmall extends React.Component {
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
        let dates = null;
        if (numberOfDates == 1) {
            const session = this.props.trip.sessions[0];
            var from = new Date(session.from_date);
            var to = new Date(session.to_date);
            dates =
                from.getDate() +
                "." +
                (from.getMonth() + 1) +
                "." +
                from.getFullYear() +
                "-" +
                to.getDate() +
                "." +
                (to.getMonth() + 1) +
                "." +
                to.getFullYear();
        } else {
            dates = numberOfDates + " dates";
        }
        let sessions = this.props.trip.sessions;

        return (
            <Link to={"/trips/" + this.props.trip.short_name}>
                <Card>
                    <div className="image-card">
                        <Card.Img
                            variant="top"
                            src="https://tesla-cdn.thron.com/delivery/public/image/tesla/21f55e82-6908-4570-9396-d76972959944/bvlatuR/std/2880x1606/Giga-Berlin-Main-Hero-Desktop"
                        />
                        <div className="trip_info">
                            <h4 className="ml-3" id="trip-title">
                                {" "}
                                {this.props.trip.name}
                            </h4>
                        </div>
                    </div>
                    <Row>
                        <Col className="d-flex flex-column date_stars">
                            <Row>{reviewStars}</Row>
                        </Col>
                        <Col className="text price">
                            <span>{this.props.trip.salary} Kč/Hod</span>
                        </Col>
                    </Row>
                </Card>
            </Link>
        );
    }
}

export default TripSmall;
