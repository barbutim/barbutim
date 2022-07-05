import React from "react";
import ModalCentered from "./ModalCentered";
import "./Modal.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

/**
 * smart gadgets achievement
 * @param props
 * @returns {JSX.Element}
 * @constructor
 */
function AchievementModal(props) {
    const [modalShow, setModalShow] = React.useState(false);

    const iconClass = "ml-2 mr-2";

    let modalButton = (
        <FontAwesomeIcon
            className={iconClass + " modalButton"}
            icon={props.icon}
            size="lg"
            onClick={() => setModalShow(true)}
        />
    );
    if (props.titleBeforeIcon) {
        modalButton = (
            <div className="modalButton" onClick={() => setModalShow(true)}>
                {props.title}
                <FontAwesomeIcon
                    className={iconClass}
                    icon={props.icon}
                    size="lg"
                />
            </div>
        );
    }

    return (
        <>
            {modalButton}

            <ModalCentered
                show={modalShow}
                onHide={() => setModalShow(false)}
                title={props.title}
                description={props.description}
                icon={props.icon}
            />
        </>
    );
}
export default AchievementModal;
