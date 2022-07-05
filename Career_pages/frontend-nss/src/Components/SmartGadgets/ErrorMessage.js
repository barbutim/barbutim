import React from "react";
import "./Modal.css";

/**
 * smart gadgets error message
 * @param props
 * @returns {JSX.Element}
 * @constructor
 */
function ErrorMessage(props) {
    return <div className={props.show ? "" : "d-none"}>{props.message}</div>;
}
export default ErrorMessage;
