import React from "react";
import { Modal, Button } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

/**
 * smart gadgets modal centered
 * @param props
 * @returns {JSX.Element}
 * @constructor
 */
function ModalCentered(props) {
    let footer = null;
    if (props && props.button) {
        footer = (
            <Modal.Footer>
                <Button
                    variant={
                        props.button.variant ? props.button.variant : "primary"
                    }
                    onClick={(event) =>
                        props.button.onClick(
                            event,
                            props.button.onClickParameter
                        )
                    }
                >
                    {props.button.title}
                </Button>
            </Modal.Footer>
        );
    }

    let description = null;
    if (props.description) {
        if (Array.isArray(props.description)) {
            description = [];
            props.description.forEach((item) => {
                description.push(<p>{item}</p>);
            });
        } else {
            description = props.description;
        }
    }

    let stars = null;
    if (props.stars) {
        stars = renderRating(props.stars);
    }

    return (
        <Modal
            {...props}
            size={props.size ? props.size : "sm"}
            aria-labelledby="contained-modal-title-vcenter"
            centered
            style={{
                textAlign: "center",
            }}
        >
            <Modal.Header closeButton>
                <Modal.Title id="contained-modal-title-vcenter">
                    {props.title}
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                {stars}
                <FontAwesomeIcon icon={props.icon} size="6x" />

                <p>{description}</p>
            </Modal.Body>
            {footer}
        </Modal>
    );
}

function renderRating(rating) {
    let starsElement = [];
    if (rating == 0) {
        return <span style={{ color: "black" }}>Trip has not any review</span>;
    }
    for (var i = 1; i <= rating; i++) {
        starsElement.push(<FontAwesomeIcon key={i} icon="star" />);
    }
    if (rating - starsElement.length >= 0.5) {
        starsElement.push(
            <FontAwesomeIcon key={starsElement.length + 1} icon="star-half" />
        );
    }
    return starsElement;
}

export default ModalCentered;
