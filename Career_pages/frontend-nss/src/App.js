import React, { useEffect } from "react";
import "./App.css";
import Router from "./Router";
import Navigation from "./Components/Navigation";
import { library } from "@fortawesome/fontawesome-svg-core";
import { BrowserRouter } from "react-router-dom";
import {
    faTrophy,
    faPowerOff,
    faCog,
    faSuitcase,
    faStar,
    faStarHalf,
    faSwimmer,
    faMedal,
    faAward,
    faCommentMedical,
    faCommentDots,
    faShieldAlt,
    faCrown,
    faWater,
    faSwimmingPool,
    faLocationArrow,
    faTrashAlt,
    faGraduationCap,
    faFish,
    faMountain,
    faFlag,
    faSnowflake,
    faHamburger,
    faPizzaSlice,
    faGlassCheers,
    faPastafarianism,
    faCompass,
    faRunning,
    faCampground,
    faChessRook,
    faChevronLeft,
    faTimes,
    faClock,
    faMapMarkerAlt,
    faMapSigns,
    faCalendarAlt,
    faUserAlt,
    faMoneyBill,
    faMinusCircle,
    faCheckCircle,
    faMoneyBillAlt,
    faAddressCard,
    faCheck,
    faRedo,
    faHammer,
    faSmileWink,
    faSearch,
} from "@fortawesome/free-solid-svg-icons";
import { faStar as emptyStar } from "@fortawesome/free-regular-svg-icons";
import { appContext } from "./appContext";
import Cookies from "js-cookie";
import { Container, Spinner } from "react-bootstrap";

library.add(
    faTrophy,
    faPowerOff,
    faCog,
    faSuitcase,
    faStar,
    faStarHalf,
    emptyStar,
    faSwimmer,
    faMedal,
    faAward,
    faCommentMedical,
    faCommentDots,
    faShieldAlt,
    faCrown,
    faWater,
    faSwimmingPool,
    faLocationArrow,
    faTrashAlt,
    faGraduationCap,
    faFish,
    faMountain,
    faFlag,
    faSnowflake,
    faHamburger,
    faPizzaSlice,
    faGlassCheers,
    faPastafarianism,
    faCompass,
    faRunning,
    faCampground,
    faChessRook,
    faChevronLeft,
    faTimes,
    faClock,
    faMapMarkerAlt,
    faMapSigns,
    faCalendarAlt,
    faUserAlt,
    faMoneyBill,
    faMinusCircle,
    faCheckCircle,
    faMoneyBillAlt,
    faAddressCard,
    faCheck,
    faRedo,
    faHammer,
    faSmileWink,
    faSearch
);

class App extends React.Component {
    state = {
        user: null,
        loginTry: false,
    };

    logout = () => {
        Cookies.remove("JSESSIONID");
        this.setState({ user: null });
    };

    login = (user) => {
        this.setState({ user: user });
    };

    componentDidMount = async () => {
        if (Cookies.get("JSESSIONID") && this.state.user == null) {
            await this.tryLogin();
        }
        this.setState({ tryLogin: true });
    };

    tryLogin = async () => {
        await fetch(BACKEND + "/user/current", {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            },
            credentials: "include",
        })
            .then((response) => {
                if (response.ok) return response.json();
                else throw Error(response.status);
            })
            .then((data) => {
                this.login(data);
            })
            .catch((error) => {
                this.logout();
                console.error(error);
            });
    };

    render() {
        const value = {
            user: this.state.user,
            logout: this.logout,
            login: this.login,
            tryLogin: this.tryLogin,
        };
        if (this.state.tryLogin) {
            return (
                <appContext.Provider value={value}>
                    <BrowserRouter>
                        <div className="App">
                            <Navigation />
                            <Router />
                        </div>
                    </BrowserRouter>
                </appContext.Provider>
            );
        } else {
            return (
                <Container className="p-5">
                    <Spinner animation="border" role="status">
                        <span className="sr-only">Loading...</span>
                    </Spinner>
                </Container>
            );
        }
    }
}

export default App;
export const BACKEND = "https://backend-nss.herokuapp.com";
