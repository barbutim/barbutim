import React from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Card, Button, Col, Row, Image } from "react-bootstrap";
import DatePicker from "react-datepicker";

class ActiveTrips extends React.Component {
    state = { enroll: null, state: null, deposit_was_paid: null };
    async componentDidMount() {
        this.setState({ enroll: this.props.trip });
        this.setState({ deposit_was_paid: this.props.trip.deposit_was_paid });
        this.setState({ state: this.props.trip.state });
    }
    render() {
        this.actionElement = (
            <Button
                className="cancel"
                onClick={(event) =>
                    this.props.funcToCancel(this, this.state.enroll)
                }
            >
                Cancel <FontAwesomeIcon icon={"trash-alt"} />
            </Button>
        );
        if (this.state.enroll == null) {
            return "null";
        } else {
            return (
                <Card className="mb-3 userTrip window radius">
                    <Card.Body className="d-flex flex-row">
                        <Col xs={4}>
                            <Card.Title className="mb-2 text-muted">
                                Meno
                            </Card.Title>
                            <Card.Text>{this.state.enroll.trip.name}</Card.Text>

                            <Card.Title className="mb-2 text-muted">
                                Miesto
                            </Card.Title>
                            <Card.Text>
                                {this.state.enroll.trip.location}
                            </Card.Text>
                        </Col>
                        <Col xs={4}>
                            <Card.Title className="mb-2 text-muted">
                                Dátum
                            </Card.Title>
                            <Card.Text>
                                <DatePicker
                                    className="form-control"
                                    selected={Date.parse(
                                        this.state.enroll.tripSession.from_date
                                    )}
                                    dateFormat="dd. MM. yyyy"
                                    disabled={true}
                                />
                                <DatePicker
                                    className="form-control"
                                    selected={Date.parse(
                                        this.state.enroll.tripSession.to_date
                                    )}
                                    dateFormat="dd. MM. yyyy"
                                    disabled={true}
                                />
                            </Card.Text>
                            <Card.Title className="mb-2 text-muted">
                                Výplata
                            </Card.Title>
                            <Card.Text>
                                {this.state.enroll.trip.salary} Kč / hod{" "}
                            </Card.Text>
                        </Col>
                        <Col xs={2}>
                            <Card.Title className="mb-2 text-muted">
                                Pridané do denníku
                            </Card.Title>
                            <Card.Text>
                                <DatePicker
                                    className="form-control"
                                    selected={Date.parse(
                                        this.state.enroll.enrollDate
                                    )}
                                    dateFormat="dd. MM. yyyy"
                                    disabled={true}
                                />
                            </Card.Text>

                            <Card.Text>
                                {this.state.state != "ACTIVE"
                                    ? "Job was canceled"
                                    : !this.state.deposit_was_paid
                                        ? this.buttonToPay
                                        : ""}
                                {this.state.state != "ACTIVE"
                                    ? ""
                                    : this.actionElement}
                            </Card.Text>
                        </Col>
                    </Card.Body>
                </Card>
            );
        }
    }
}

export default ActiveTrips;
