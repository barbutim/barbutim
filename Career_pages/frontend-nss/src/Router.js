import React, { useContext } from "react";
import Home from "./Components/Home/Trip/Index";
import Logout from "./Components/Auth/Logout";
import Login from "./Components/Auth/Login";
import Profile from "./Components/Profile/Profile";
import Register from "./Components/Register";
import { Redirect, Switch, Route } from "react-router-dom";
import TripDetail from "./Components/Home/Trip/Detail";
import { appContext } from "./appContext";
import IndexFilter from "./Components/Home/Trip/IndexFilter";
import ProfileTrips from "./Components/Profile/Trips/ProfileTrips";

/**
 * to switch between pages
 * @param constant
 * @returns {JSX.Element}
 * @constructor
 */
function Router(constant) {
    const context = useContext(appContext);

    const allowAuth = (component) => {
        if (context.user != null) {
            return component;
        } else {
            return <Redirect to={{ pathname: "/login" }} />;
        }
    };

    const allowGuest = (component) => {
        if (context.user === null) {
            return component;
        } else {
            return <Redirect to={{ pathname: "/" }} />;
        }
    };

    const allowGuestAndAllRoles = (component) => {
        return component;
    };

    return (
        <div>
            <Switch>
                <Route path="/profile" component={Profile} />
                <Route path="/profile/trips" component={ProfileTrips} />
                <Route
                    path="/login"
                    exact={true}
                    render={() => {
                        return allowGuest(<Login />);
                    }}
                />
                <Route
                    path="/logout"
                    render={() => {
                        return allowAuth(<Logout />);
                    }}
                />
                <Route
                    path="/register"
                    exact={true}
                    render={() => {
                        return allowGuest(<Register />);
                    }}
                />
                <Route path="/" exact={true} component={Home} />
                <Route path="/trips" exact={true} component={IndexFilter} />
                <Route
                    path="/trips/:id"
                    render={() => {
                        return allowGuestAndAllRoles(<TripDetail />);
                    }}
                />
            </Switch>
        </div>
    );
}

export default Router;
