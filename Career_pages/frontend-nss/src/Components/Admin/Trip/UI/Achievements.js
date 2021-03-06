import React from "react";
import { Form } from "react-bootstrap";
import AchievementFormGroup from "./AchievementFormGroup";

/**
 * achievements
 * @param props
 * @returns {JSX.Element}
 * @constructor
 */
function Achievements(props) {
    let requiredAchievements = null;
    let gainAchievements = null;
    if (props.itemsRequired && props.itemsGain) {
        requiredAchievements = (
            <AchievementFormGroup
                label="Required achievements"
                items={props.itemsRequired}
                multiple={true}
                formInputName="requeired_achievements"
                selected={props.selectedRequired}
                onChangeMethod={{
                    special: (event) =>
                        props.onChangeMethod(
                            event,
                            "required_achievements_special"
                        ),
                    categorized: (event) =>
                        props.onChangeMethod(
                            event,
                            "required_achievements_categorized"
                        ),
                }}
                uneditable={props.uneditable ? true : false}
            />
        );
        gainAchievements = (
            <AchievementFormGroup
                label="Gain achievements"
                items={props.itemsGain}
                formInputName="gain_achievements_special"
                selected={props.selectedGain}
                onChangeMethod={(event) =>
                    props.onChangeMethod(event, "gain_achievements_special")
                }
                uneditable={props.uneditable ? true : false}
            />
        );
    }

    return (
        <Form.Row className="d-flex ml-5">
            {requiredAchievements}

            {gainAchievements}
        </Form.Row>
    );
}
export default Achievements;
