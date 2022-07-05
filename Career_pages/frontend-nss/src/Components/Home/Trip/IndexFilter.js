import React from "react";
import { Container, Card, Form, Row, Col, Button } from "react-bootstrap";
import Spinner from "react-bootstrap/Spinner";
import "react-bootstrap-range-slider/dist/react-bootstrap-range-slider.css";
import TripMedium from "./TripMedium";
import { BACKEND } from "../../../App";

/**
 * Index job offer filter
 */
class IndexFilter extends React.Component {
    state = {
        trips: null,
        filter: {
            from: null,
            to: null,
            price: 50,
            words: [],
        },
    };

    componentDidUpdate(prevState) {
        if (prevState.location.search != this.props.location.search) {
            const newState = { ...this.state };
            newState.filter["words"] = this.searchParameter();
            this.setState({ state: newState });
            this.fetchData();
        }
    }

    searchParameter = () => {
        const urlParams = new URLSearchParams(this.props.location.search);
        const parameters = urlParams.get("search");
        return parameters ? parameters.split(" ") : null;
    };

    inputUpdateHandler = (event, inputName) => {
        const newState = { ...this.state.filter };
        if (inputName == "price")
            newState["price"] = Number(event.target.value);
        else if (inputName == "from" || inputName == "to") {
            let newDate = new Date(event);
            newDate.setTime(
                newDate.getTime() - new Date().getTimezoneOffset() * 60 * 1000
            );
            newState[inputName] = newDate;
        }
        this.setState({ filter: newState });
    };

    searchUrl = () => {
        let filterUrl = BACKEND + "/trip/filter?";
        let filter = false;

        const searchParameters = this.searchParameter();

        if (searchParameters && searchParameters.length > 0) {
            searchParameters.forEach((param) => {
                if (filter) {
                    filterUrl += "&search=" + param;
                } else {
                    filterUrl += "search=" + param;
                }

                filter = true;
            });
        }
        if (this.state.filter.price) {
            if (filter) {
                filterUrl += "&min_price=" + this.state.filter.price;
            } else {
                filterUrl += "min_price=" + this.state.filter.price;
            }
            filter = true;
        }
        if (this.state.filter.from) {
            const date = this.getFormatedDate(this.state.filter.from);
            if (filter) {
                filterUrl += "&from_date=" + date;
            } else {
                filterUrl += "from_date=" + date;
            }
            filter = true;
        }
        if (this.state.filter.to) {
            const date = this.getFormatedDate(this.state.filter.to);
            if (filter) {
                filterUrl += "&to_date=" + date;
            } else {
                filterUrl += "to_date=" + date;
            }
            filter = true;
        }
        return filter ? filterUrl : BACKEND + "/trip";
    };

    getFormatedDate = (date) => {
        const month = ("0" + (date.getMonth() + 1)).slice(-2);
        const day = ("0" + date.getDate()).slice(-2);

        return date.getFullYear() + "-" + month + "-" + day;
    };

    fetchData = async (event = null) => {
        if (event) {
            event.preventDefault();
        }
        const url = this.searchUrl();
        await fetch(url, {
            method: "GET",
            mode: "cors",
            credentials: "include",
            headers: {
                "Content-Type": "application/json",
            },
        })
            .then((response) => {
                console.log(response);
                if (response.ok) return response.json();
                else console.error(response.status);
            })
            .then((data) => {
                console.log(data);
                this.setState({ trips: data });
            })
            .catch((error) => {
                console.error(error);
            });
    };

    async componentDidMount() {
        const newState = { ...this.state };
        newState.filter.words = this.searchParameter();
        if (newState.filter.words != this.state.filter.words) {
            this.setState(newState);
        }

        this.fetchData();
    }

    refresh = () => {
        this.setState({
            filter: {
                from: null,
                to: null,
                price: 50,
                words: this.state.filter.words,
            },
        });
    };

    render() {
        if (this.state.trips == null) {
            return (
                <Container className="p-5">
                    <Spinner animation="border" role="status">
                        <span className="sr-only">Loading...</span>
                    </Spinner>
                </Container>
            );
        } else {
            return (
                <Container className="searchTrips mt-5">
                    <Row>
                        <Col className="col-md-8">
                            {this.state.trips.map((trip) => {
                                return (
                                    <Row>
                                        <TripMedium
                                            key={trip.short_name}
                                            highlightWords={
                                                this.state.filter.words
                                            }
                                            trip={trip}
                                        />
                                    </Row>
                                );
                            })}
                        </Col>
                    </Row>
                </Container>
            );
        }
    }
}

export default IndexFilter;
