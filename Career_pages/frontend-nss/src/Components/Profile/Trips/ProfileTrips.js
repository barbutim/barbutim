import React from "react";
import Profile from "../Profile";
import { Container, Button } from "react-bootstrap";
import TripHistory from "./TripHistory";
import ActiveTrips from "./ActiveTrips";
import Spinner from "react-bootstrap/Spinner";
import { appContext } from "../../../appContext";
import { withRouter } from "react-router-dom";
import { BACKEND } from "../../../App";

/**
 * Profile your own offers
 */
class ProfileTrips extends Profile {
    state = {
        user: null,
        active_trips: null,
        archive_trips: null,
        refresh: false,
    };

    static contextType = appContext;

    async componentDidMount() {
        await this.fetchData();
        this.setState({ user: this.context.user });
    }

    fetchData = async () => {
        await fetch(BACKEND + "/enrollment/complete", {
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
                this.setState({ archive_trips: data });
            })
            .catch((error) => {
                console.error(error);
            });

        await fetch(BACKEND + "/enrollment/active", {
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
                this.setState({ active_trips: data });
            })
            .catch((error) => {
                console.error(error);
            });
    };

    async componentDidUpdate() {
        if (this.state.refresh) {
            await this.setState({ refresh: false, archive_trips: null });
            await this.fetchData();
        }
    }

    refreshComponent = async () => {
        await this.setState({ refresh: true });
    };

    paymentForm = null;
    closeValidateWindow() {
        document.querySelector(".popup_background").classList.add("hidden");
        this.setState({ paymentForm: null });
        this.setState({ viewForm: false });
    }
    cancelTrip(props, enrollment) {
        setTimeout(
            function () {
                props.setState({ state: "CANCELLED" });
                console.log(props);
                this.closeValidateWindow();
                fetch(BACKEND + "/enrollment/cancel/" + enrollment.id, {
                    method: "POST",
                    mode: "cors",
                    credentials: "include",
                    headers: {
                        "Content-Type": "application/json",
                    },
                }).then((response) => {
                    if (response.ok) {
                        window.setTimeout(function () {
                            alert("Job was cancelled");
                        }, 500);
                    } else {
                        alert("Error witch cancel enrollment");
                    }
                });
            }.bind(this),
            1000
        );
    }
    switchToActive(target) {
        target.classList.add("active");
        document.querySelector("#switchToArchive").classList.remove("active");
        document.querySelector(".archiveTrips").classList.remove("active");
        document.querySelector(".activeTrips").classList.add("active");
    }
    switchToArchive(target) {
        target.classList.add("active");
        document.querySelector("#switchToActive").classList.remove("active");
        document.querySelector(".archiveTrips").classList.add("active");
        document.querySelector(".activeTrips").classList.remove("active");
    }
    payDeposit(state, enrollment) {
        setTimeout(function () {
            state.setState({ deposit_was_paid: true });
            fetch(BACKEND + "/enrollment/changePayment/" + enrollment.id, {
                method: "POST",
                mode: "cors",
                credentials: "include",
                headers: {
                    "Content-Type": "application/json",
                },
            }).then((response) => {
                if (response.ok) {
                    window.setTimeout(function () {
                        alert("Job was sucessfuly paid");
                    }, 500);
                } else {
                    alert("Error with paying");
                }
            });
        }, 1000);
        this.component.closeValidateWindow();
    }
    openPayWindow(enrollment, trip) {
        console.log(this);
        const popup = document.querySelector(".popup_background");

        popup.classList.remove("hidden");
        this.component.paymentForm = (
            <ConfirmPayment
                payFunc={this.component.payDeposit}
                state={trip}
                enrollment={enrollment}
                component={this.component}
                cancelled={this.component.closeValidateWindow}
                price={enrollment.trip.deposit}
            />
        );
        this.component.setState({ viewForm: true });
    }
    openCancelWindow(trip, enrollment) {
        const popup = document.querySelector(".popup_background");
        popup.classList.remove("hidden");
        this.component.paymentForm = (
            <CancelTripForm
                state={trip}
                enrollment={enrollment}
                component={this.component}
            />
        );
        this.component.setState({ viewForm: true });
    }
    reviewExist(id) {
        return false;
    }
    renderActiveTrip(activetrips) {
        return activetrips
            .sort(function (a, b) {
                return parseFloat(a.id) - parseFloat(b.id);
            })
            .slice(0)
            .reverse()
            .map((trip) => (
                <ActiveTrips
                    key={trip.name}
                    trip={trip}
                    funcToPay={this.openPayWindow}
                    funcToCancel={this.openCancelWindow}
                    component={this}
                    refreshFunction={this.refreshComponent}
                />
            ));
    }
    renderArchiveTrip(archiveTrips) {
        return archiveTrips
            .sort(function (a, b) {
                return parseFloat(a.id) - parseFloat(b.id);
            })
            .slice(0)
            .reverse()
            .map((trip) => (
                <TripHistory
                    key={trip.name}
                    reviewExists={this.reviewExist(trip.short_name)}
                    trip={trip}
                    refreshFunction={this.refreshComponent}
                />
            ));
    }
    render() {
        if (
            this.state.active_trips == null ||
            this.state.archive_trips == null
        ) {
            return (
                <Container className="p-5">
                    <Spinner animation="border" role="status">
                        <span className="sr-only">Loading...</span>
                    </Spinner>
                </Container>
            );
        } else {
            return (
                <Container>
                    <div className="switch">
                        <span
                            id="switchToActive"
                            className="active"
                            onClick={(event) =>
                                this.switchToActive(event.target)
                            }
                        >
                            Akt??vne
                        </span>
                        <span
                            id="switchToArchive"
                            onClick={(event) =>
                                this.switchToArchive(event.target)
                            }
                        >
                            Arch??v
                        </span>
                    </div>
                    <div id="tripsElementBlock">
                        <div className="activeTrips active">
                            {this.renderActiveTrip(this.state.active_trips)}
                        </div>
                        <div className="archiveTrips">
                            {this.renderArchiveTrip(this.state.archive_trips)}
                        </div>
                    </div>
                    <div className="popup_background hidden">
                        {this.state.viewForm ? this.paymentForm : ""}
                    </div>
                </Container>
            );
        }
    }
}

function ConfirmPayment(props) {
    return (
        <div className="window radius pay_deposit customScroll">
            <h5>Pay deposit</h5>
            <p>Do you really want to pay the deposit?</p>
            <p>
                Price: <span id="priceToPay">{props.price}</span>,-
            </p>
            <div>
                <Button
                    className="submit"
                    onClick={(event) =>
                        props.payFunc(props.state, props.enrollment)
                    }
                >
                    Yes
                </Button>
                <Button
                    className="cancel"
                    onClick={(event) => props.component.closeValidateWindow()}
                >
                    No
                </Button>
            </div>
        </div>
    );
}
function CancelTripForm(props) {
    console.log(props);
    console.log(props.enrollment);
    return (
        <div className="window radius pay_deposit customScroll">
            <h5>Cancel job</h5>
            <p>Do you really want cancel the job?</p>
            <p>This operation cannot be undone</p>
            <div>
                <Button
                    className="submit"
                    onClick={(event) =>
                        props.component.cancelTrip(
                            props.state,
                            props.enrollment
                        )
                    }
                >
                    Yes
                </Button>
                <Button
                    className="cancel"
                    onClick={(event) => props.component.closeValidateWindow()}
                >
                    No
                </Button>
            </div>
        </div>
    );
}
export default withRouter(ProfileTrips);
