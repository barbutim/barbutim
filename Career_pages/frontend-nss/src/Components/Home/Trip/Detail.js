import React from "react";
import Card from "react-bootstrap/Card";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Button, Row, Col, Container, Image, ListGroup } from "react-bootstrap";
import { Link, withRouter } from "react-router-dom";
import Spinner from "react-bootstrap/Spinner";
import { Form, Modal } from "react-bootstrap";
import AchievmentModal from "../../SmartGadgets/AchievementModal";
import { appContext } from "../../../appContext";
import { BACKEND } from "../../../App";

/**
 * Detail of concrete offer
 */
class Detail extends React.Component {
    state = {
        trip: null,
        selectedSession: {
            id: null,
            from_date: null,
            to_date: null,
            price: null,
        },
        job_journal: null,
    };

    formIsValid = false;
    static contextType = appContext;
    sessionsIds = [];
    async componentDidMount() {
        const response = await fetch(
            BACKEND + "/trip/" + this.props.match.params.id,
            {
                method: "GET",
                mode: "cors",
                credentials: "include",
                headers: {
                    "Content-Type": "application/json",
                },
            }
        );
        const data = await response.json();

        if (this.context.user != null) {
            await fetch(BACKEND + "/user/jobJournal", {
                method: "GET",
                mode: "cors",
                credentials: "include",
                headers: {
                    "Content-Type": "application/json",
                },
            })
                .then((response) => {
                    if (response.ok) return response.json();
                    else console.error(response.status);
                })
                .then((data) => {
                    this.setState({ job_journal: data });
                })
                .catch((error) => {
                    console.error(error);
                });
        }
        console.log(this.state);

        this.setState({ trip: data });
        if (data.sessions.length > 0) {
            this.setState({ selectedSession: data.sessions[0] });
        }
        this.sessionsIds = data.sessions.map(function (session) {
            return session.id;
        });
    }
    sessionTripChange = async (selectElement) => {
        const optId = selectElement.value;
        if (this.sessionsIds.includes(parseInt(optId))) {
            const session = this.state.trip.sessions.find(
                (session) => session.id == optId
            );
            this.setSelectedSession(session);
        }
    };
    setSelectedSession(session) {
        this.setState({ selectedSession: session });
    }
    submitTrip(event, formElement) {
        event.preventDefault();
        console.log(this.state.selectedSession);
        let checboxIsChecked = false;
        checboxIsChecked = document.querySelector("#checkboxAgreement input")
            .checked;
        if (checboxIsChecked && this.formIsValid) {
            fetch(BACKEND + "/trip/" + this.state.trip.short_name, {
                method: "POST",
                mode: "cors",
                credentials: "include",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(this.state.selectedSession),
            }).then((response) => {
                if (response.ok) {
                    window.setTimeout(function () {
                        alert("Job was added to your travel journal");
                        document.location.reload();
                    }, 500);
                }
                else alert("Error: somethhing goes wrong");
            });
        }
    }

    validateUserAchievement(achievement, userList) {
        return userList.some((item) => item.id == achievement.id);
    }

    /**
     * Validation before purchase trip.
     * @param {event} event
     */
    validatePurchase(event) {
        event.preventDefault();
        document.querySelector(".popup_background").classList.remove("hidden");
        let specialValidate = false;
        let certificateslValidate = false;
        let categorizedValidate = false;
        let levelPassed = false;

        const req_ach_special = this.state.trip.required_achievements_special;
        const req_ach_categorized = this.state.trip
            .required_achievements_categorized;
        const req_cerf = this.state.trip.required_achievements_certificate;
        const minlevel = this.state.trip.required_level;

        specialValidate = req_ach_special.every((val) =>
            this.validateUserAchievement(
                val,
                this.state.job_journal ? this.state.job_journal.special : []
            )
        );
        categorizedValidate = req_ach_categorized.every((val) =>
            this.validateUserAchievement(
                val,
                this.state.job_journal ? this.state.job_journal.categorized : []
            )
        );
        certificateslValidate = req_cerf.every((val) =>
            this.validateUserAchievement(
                val,
                this.state.job_journal
                    ? this.state.job_journal.certificates
                    : []
            )
        );
        levelPassed =
            (this.state.job_journal
                ? Number(this.state.job_journal.level)
                : 0) >= minlevel;

        if (
            specialValidate &&
            categorizedValidate &&
            certificateslValidate &&
            levelPassed
        ) {
            this.formIsValid = true;
        } else {
            document.querySelector("#confirmPurchase").style.display = "none";
            document.querySelector("#checkboxAgreement").style.display = "none";
            document.querySelector("#validationFalse").style.display = "block";
        }
    }
    closeValidateWindow(element) {
        document.querySelector(".popup_background").classList.add("hidden");
    }

    /**
     * Render rating stars.
     * @param {Number} rating
     */
    renderRating(rating) {
        let starsElement = [];
        if (rating == 0) {
            return null;
        }
        for (var i = 1; i <= rating; i++) {
            starsElement.push(<FontAwesomeIcon key={i} icon="star" />);
        }
        if (rating - starsElement.length >= 0.5) {
            starsElement.push(
                <FontAwesomeIcon
                    key={starsElement.length + 1}
                    icon="star-half"
                />
            );
        }
        return starsElement;
    }

    /**
     * Render achievements.
     * @param {*} achievements
     * @param {*} message
     */
    renderAchievements(achievements, message = "No achievements are required") {
        if (achievements.length == 0) {
            return message;
        }
        let toReturn = [];
        achievements.forEach((element) => {
            toReturn.push(
                <ListGroup.Item>
                    <AchievmentModal
                        titleBeforeIcon={true}
                        icon={element.icon}
                        title={element.name}
                        description={element.description}
                    />
                </ListGroup.Item>
            );
        });
        return toReturn;
    }

    dateTimeFormater(dateToFormat) {
        const date = new Date(dateToFormat);
        let formated = "";
        formated +=
            date.getDate() +
            "." +
            (date.getMonth() + 1) +
            "." +
            date.getFullYear();
        return formated;
    }

    render() {
        if (this.state.trip === null) {
            return (
                <Container className="p-5">
                    <Spinner animation="border" role="status">
                        <span className="sr-only">Loading...</span>
                    </Spinner>
                </Container>
            );
        } else {
            let options = null;
            let sessionBlock = null;
            let dateTitle = "Dátum";

            let optionArray = [];
            this.state.trip.sessions.forEach((element) => {
                optionArray.push(
                    <option key={element.id} value={element.id}>
                        {element.from_date + " " + element.to_date}
                    </option>
                );
            });
            if (this.state.trip.sessions == 0) {
                options = <div>Trip nemá žádnou session</div>;
                sessionBlock = (
                    <div className="trip_price">Nemôžeš sa zapísať</div>
                );
            } else if (this.context.user == null) {
                options = (
                    <Form.Control
                        as="select"
                        id="dateSessionSelect"
                        onChange={(event) =>
                            this.sessionTripChange(event.target)
                        }
                    >
                        {optionArray}
                    </Form.Control>
                );
                sessionBlock = (
                    <div>
                        <div className="trip_price">
                            <span id="tripPrice">
                                {" "}
                                {this.state.trip.salary}
                            </span>
                            Kč
                        </div>
                        <div>
                            Zapísať sa môžu iba prihlásený uživatelia.{" "}
                            <Link to="/login">Login</Link>
                        </div>
                    </div>
                );
            } else {
                options = (
                    <Form.Control
                        as="select"
                        id="dateSessionSelect"
                        onChange={(event) =>
                            this.sessionTripChange(event.target)
                        }
                    >
                        {optionArray}
                    </Form.Control>
                );
                sessionBlock = (
                    <div>
                        <div className="trip_price">
                            <span id="tripPrice">
                                {" "}
                                {this.state.trip.salary}
                            </span>
                            Kč
                        </div>
                        <Button
                            className="submit"
                            variant="primary"
                            type="submit"
                            onClick={(event) => this.validatePurchase(event)}
                        >
                            {" "}
                            Zapísať sa{" "}
                        </Button>
                    </div>
                );
            }
            if (this.state.trip.sessions.length > 1) {
                dateTitle = "Dátumy";
            }
            const reviews = this.state.trip.jobReviewDtos;
            const reviewsBlock = reviews.map((review) => (
                <div className="review">
                    <Row>
                        <Col className="rev_author" xs={6}>
                            <FontAwesomeIcon icon="user-alt" />
                            <span>{review.author}</span>
                            <span className="text-muted">
                                {this.dateTimeFormater(review.date)}
                            </span>
                        </Col>
                        <Col className="rev_rating" xs={6}>
                            {this.renderRating(review.rating)}
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <p className="note">{review.note}</p>
                        </Col>
                    </Row>
                </div>
            ));
            if (reviewsBlock.length == 0) {
                reviewsBlock.push(
                    <Row className="d-flex justify-content-center">
                        Brigáda zatiaľ nemá žiadne hodnotenie.
                    </Row>
                );
            }
            return (
                <Container id="trip_detail">
                    <Card className="mb-3 trip_main">
                        <Card.Body className="d-flex flex-row">
                            <Col xs={5} className="image">
                                <Image
                                    src="https://tesla-cdn.thron.com/delivery/public/image/tesla/21f55e82-6908-4570-9396-d76972959944/bvlatuR/std/2880x1606/Giga-Berlin-Main-Hero-Desktop"
                                    rounded
                                />
                            </Col>
                            <Col xs={7} className="trip_info">
                                <Row className="d-flex flex-column">
                                    <Col>
                                        <Card.Title className="trip_name">
                                            {this.state.trip.name}
                                        </Card.Title>
                                    </Col>
                                </Row>
                                <Form>
                                    <Row>
                                        <Col>
                                            <Card.Title className="mb-2 text-muted">
                                                <FontAwesomeIcon icon="map-marker-alt" />{" "}
                                                Miesto
                                            </Card.Title>
                                            <Card.Text>
                                                {this.state.trip.location}
                                            </Card.Text>
                                        </Col>
                                        <Col>
                                            <div className="rev-price-buy">
                                                <div className="review_element">
                                                    {this.renderRating(
                                                        this.state.trip.rating
                                                    )}
                                                </div>
                                            </div>
                                        </Col>
                                    </Row>
                                </Form>
                            </Col>
                        </Card.Body>
                    </Card>
                    <Row>
                        <Col className="col-4 trip_restriction">
                            <Card className="mb-3">
                                <Card.Body>
                                    <Card.Subtitle className="mb-2 text-muted">
                                        Mzda:
                                    </Card.Subtitle>
                                    <Card.Text>
                                        {this.state.trip.salary} Kč / hod
                                    </Card.Text>
                                </Card.Body>
                            </Card>
                        </Col>
                        <Col className="col-8">
                            <Card className="mb-5">
                                <Card.Body>
                                    <Card.Title>Popis pracovného miesta:</Card.Title>
                                    <Card.Text>
                                        {this.state.trip.description}
                                    </Card.Text>
                                </Card.Body>
                            </Card>
                        </Col>
                    </Row>
                    <form
                        onSubmit={(event) => function () {
                            this.submitTrip(event, event.target)
                            console.log(event);
                            console.log(event.target);
                            }
                        }
                    >
                    <Row>
                        <Col></Col>
                        <Col xs={6}>
                            <Button
                                id="confirmPurchase"
                                className="submit"
                                variant="primary"
                                type="submit"
                            >
                                Prihlásiť sa
                            </Button>
                        </Col>
                        <Col></Col>
                    </Row>
                    </form>
                </Container>
            );
        }
    }
}

export default withRouter(Detail);
