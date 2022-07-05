import React from "react";
import { Form, Col } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

/**
 * Achievements input
 * @param props
 * @returns {JSX.Element}
 * @constructor
 */
function AchievementInput(props) {
    return (
        <>
            <Form.Check.Label>
                {props.uneditable ? (
                    <Form.Check.Input
                        type="checkbox"
                        key={props.element.id}
                        defaultValue={props.element.id}
                        checked={props.selected}
                        disabled
                    />
                ) : (
                    <Form.Check.Input
                        type="checkbox"
                        key={props.element.id}
                        defaultValue={props.element.id}
                        onChange={props.onChangeMethod}
                        checked={props.selected}
                    />
                )}
                <FontAwesomeIcon icon={props.element.icon} size="lg" />
                {props.element.name}
            </Form.Check.Label>
        </>
    );
}
export default AchievementInput;
