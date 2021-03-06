import React from "react";
import Form from "react-bootstrap/Form";
import { Container, Col, Button, Row, Spinner } from "react-bootstrap";
import rules from "../../Files/validationRules.json";
import {
    formValidation,
    validationFeedback,
    validationClassName,
} from "../../Validator";
import MyAlert from "../SmartGadgets/MyAlert";
import { withRouter } from "react-router-dom";
import { appContext } from "../../appContext";
import { BACKEND } from "../../App";

/**
 * Login into the account
 */
class Login extends React.Component {
    static contextType = appContext;
    state = {
        form: {
            loginTry: false,
            loginUnsuccess: false,
            isValid: false,
            elements: {
                email: {
                    touched: false,
                    valid: false,
                    validationRules: rules.login.email,
                },
                password: {
                    touched: false,
                    valid: false,
                    validationRules: rules.login.password,
                },
            },
        },
        user: {
            email: null,
            password: null,
        },
    };

    inputUpdateHandler = async (event, nameOfFormInput) => {
        const stringProperties = ["email", "password"];
        let newState = { ...this.state.user };
        if (stringProperties.includes(nameOfFormInput)) {
            newState[nameOfFormInput] = event.target.value;
        }
        await this.setState({ user: newState });
        if (
            this.state.form.elements.hasOwnProperty(nameOfFormInput) &&
            this.state.form.elements[nameOfFormInput].touched
        ) {
            this.validateForm();
        }
    };

    submitHandler = async (event) => {
        event.preventDefault();
        await this.validateForm();
        if (this.state.form.isValid) {
            fetch(BACKEND + "/login", {
                method: "POST",
                mode: "cors",
                credentials: "include",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(this.state.user),
            })
                .then((response) => {
                    if (response.ok) {
                        return response.json();
                    } else {
                        this.props.history.push({
                            alert: null,
                        });
                        this.props.history.push({
                            alert: (
                                <MyAlert
                                    variant="danger"
                                    heading="Incorrect credentials"
                                    text="Email or password is incorect. Try again."
                                />
                            ),
                        });
                        this.context.logout();
                        return null;
                    }
                })
                .then((data) => {
                    this.context.login(data);
                })
                .catch(() => {
                    this.props.history.push({
                        alert: (
                            <MyAlert
                                variant="danger"
                                heading="Something goes wrong"
                                text="There is a small problem. Please try again in a few minutes."
                            />
                        ),
                    });
                });
        }
    };

    validateForm = async () => {
        const newState = { ...this.state.form };
        formValidation(newState, this.state.user);
        await this.setState({ form: newState });
    };

    render() {
        let loginNotSuccess = null;
        if (this.state.form.loginTry) {
            if (this.state.form.loginUnsuccess) {
                loginNotSuccess = (
                    <MyAlert
                        variant="danger"
                        text="Email or password is invalid."
                        heading="Login not successful!"
                    />
                );
            }
        }

        /**
         * Alert (flash message) from this.props.location.alert
         */
        let alert = null;
        if (
            this.props.location &&
            this.props.location.hasOwnProperty("alert")
        ) {
            alert = this.props.location.alert;
        }

        return (
            <Container className="login_container">
                <Row>
                    <Form
                        className="window radius login_form"
                        onSubmit={this.submitHandler}
                    >
                        <Form.Group controlId="formBasicEmail">
                            <Form.Label>E-mail</Form.Label>
                            <Form.Control
                                type="email"
                                placeholder="Zadajte svoj e-mail..."
                                onChange={(event) =>
                                    this.inputUpdateHandler(event, "email")
                                }
                                className={validationClassName(
                                    "email",
                                    this.state.form
                                )}
                            />
                            <div class="invalid-feedback">
                                {validationFeedback("email", this.state.form)}
                            </div>
                        </Form.Group>

                        <Form.Group controlId="formBasicPassword">
                            <Form.Label>Heslo</Form.Label>
                            <Form.Control
                                type="password"
                                placeholder="Zadajte svoje heslo..."
                                onChange={(event) =>
                                    this.inputUpdateHandler(event, "password")
                                }
                                className={validationClassName(
                                    "password",
                                    this.state.form
                                )}
                            />
                            <div class="invalid-feedback">
                                {validationFeedback(
                                    "password",
                                    this.state.form
                                )}
                            </div>
                        </Form.Group>
                        <Button
                            variant="primary"
                            type="submit"
                            className="submit"
                        >
                            Prihl??si??
                        </Button>
                    </Form>
                </Row>
                {alert}
            </Container>
        );
    }
}

export default withRouter(Login);
