import { collection, getDocs } from "firebase/firestore";
import { db } from "../services/config";

export async function getDbCollection(dbList) {
    var a = [];
    const querySnapshot = await getDocs(collection(db, dbList));
    querySnapshot.forEach(snapshot => {
        a.push({
            id: snapshot.id,
            data: snapshot.data(),
        });
    });
    return a;
}