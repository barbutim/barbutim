import React from "react";
import { Container, Row, Col } from "react-bootstrap";
import Image from "react-bootstrap/Image";
import { appContext } from "../../appContext";
import Spinner from "react-bootstrap/Spinner";

/**
 * View profile
 */
class Profile extends React.Component {
    static contextType = appContext;
    state = { user: null };
    async componentDidMount() {
        this.setState({ user: this.context.user });
    }

    render() {
        const flexRow = "d-flex justify-content-center";
        const marginTop = "mt-5";
        const flexRowWithMgBtn = flexRow;

        if (this.state.user == null) {
            return (
                <Container className="p-5">
                    <Spinner animation="border" role="status">
                        <span className="sr-only">Loading...</span>
                    </Spinner>
                </Container>
            );
        } else {
            return (
                <Container id="userProfileMain">
                    <Row className={[flexRowWithMgBtn, marginTop]}>
                        <h3>Profil užívateľa</h3>
                    </Row>
                    <Row className={flexRowWithMgBtn}>
                        <Col xs={4}>
                            <div className="userPhoto window radius">
                                <Image
                                    src="https://www.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png"
                                    className="radius"
                                />
                            </div>
                        </Col>
                        <Col>
                            <div className="window radius">
                                <Row>
                                    <Col className="user_info">
                                        <div>
                                            <label>Meno a priezvisko:</label>
                                            <h5>
                                                {this.state.user.firstName}{" "}
                                                {this.state.user.lastName}
                                            </h5>
                                        </div>
                                    </Col>
                                    <Col className="user_adress">
                                        <div>
                                            <label>Adresa:</label>
                                            <p>
                                                {this.state.user.address.street}{" "}
                                                {this.state.user.address.houseNumber}
                                            </p>
                                            <p>
                                                {this.state.user.address.city}{" "}
                                                {this.state.user.address.zipCode}
                                            </p>
                                            <p>
                                                {this.state.user.address.country}
                                            </p>
                                        </div>
                                    </Col>
                                </Row>
                            </div>
                        </Col>
                    </Row>
                </Container>
            );
        }
    }
}

export default Profile;
