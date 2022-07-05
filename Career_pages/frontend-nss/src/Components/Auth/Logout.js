import React from "react";
import { Container, Spinner } from "react-bootstrap";
import { Redirect } from "react-router-dom";
import { appContext } from "../../appContext";
import { BACKEND } from "../../App";

/**
 * Logout from account
 */
class Logout extends React.Component {
    state = {
        logout: false,
    };

    static contextType = appContext;

    componentDidMount() {
        fetch(BACKEND + "/logout", {
            method: "GET",
            mode: "cors",
            credentials: "include",
        }).then((response) => {
            if (response.ok) {
                this.setState({ logout: true });
                this.context.logout();
            } else {
                return null;
            }
        });
    }

    render() {
        if (this.state.logout) {
            return <Redirect to="/login" />;
        } else {
            return (
                <Container className="p-5 mt-5">
                    <Spinner animation="border" role="status">
                        <span className="sr-only">Loading...</span>
                    </Spinner>
                </Container>
            );
        }
    }
}

export default Logout;
