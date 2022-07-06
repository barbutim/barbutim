import { getAllMuzikals } from "./ApiMuzikal";
import { db } from "./config";

export const pudDataFromApiToDB =  async() => {

    var data = await getAllMuzikals();

    data.Events.filter(item => item.SmallImageUrl!=="https://www.londontheatredirect.com")
    .forEach(item => {
            db.collection("muzikals").add({
                EventId: item.EventId,
                Name: item.Name,
                SmallImageUrl: item.SmallImageUrl,
                Description: item.Description,
                CurrentPrice: item.CurrentPrice,
                StartDate: item.StartDate,
                EndDate: item.EndDate,
                RunningTime: item.RunningTime,
                MinimumAge: item.MinimumAge
            });
        });
}

export function create(item) {

    console.log(item);

    db.collection("muzikals").add({
        EventId : item.EventId,
        Name : item.Name,
        SmallImageUrl : item.SmallImageUrl,
        Description : item.Description,
        CurrentPrice : item.CurrentPrice,
        StartDate : item.StartDate,
        EndDate : item.EndDate,
        RunningTime : item.RunningTime,
        MinimumAge : item.MinimumAge
    });
}

export function createMuzikal(data) {
    db.collection("muzikals").add(data);
}

export function updateMuzikal(data) {
    db.collection("muzikals").doc(data.id).update(data.data);
}

export function deleteMuzikal(id) {
    db.collection("muzikals").doc(id).delete();
}
