export function getApiConnection () {
   return fetch('https://api.londontheatredirect.com/rest/v2/Events',
        {"method": "GET",
        "headers":{
            "Api-Key": "nxabz6nf55e4a332cwjhnnjg",
            "Content-Type": "application/json"
        }
        })
        .catch(err => {
            console.log(err)
        })
}

export const getAllMuzikals = async () => {
    var allMuzikals = [];

    await getApiConnection().then(res => res.json()).then(json => 
        {
            allMuzikals = json;
        })           
    return allMuzikals
} 

export const getMuzikalById = async (id) => {

    var muzikal = null
    var allMuzikals = await getAllMuzikals()
    muzikal = allMuzikals.Events.filter((item) => item.EventId === id)
    return muzikal
}





