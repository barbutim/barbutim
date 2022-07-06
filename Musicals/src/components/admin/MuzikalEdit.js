import React, {Component} from 'react';
import {db} from '../../services/config'
import TextareaAutosize from '@mui/base/TextareaAutosize';
import "../../App.css"
import {Link} from 'react-router-dom'
import {updateMuzikal, create, deleteMuzikal} from '../../services/crud'

class MuzikalEdit extends Component {

    constructor(props) {

        super(props);
        this.state = {
            muzikalId: 0,
            dataDB: [],
            isCreateMode: false,
            dataNew:{
                    EventId : 0,
                    Name : '',
                    SmallImageUrl : '',
                    Description : '',
                    CurrentPrice : '',
                    StartDate : '',
                    EndDate : '',
                    RunningTime : '',
                    MinimumAge : ''
                }
            ,

        }
    }

    componentDidMount() {
        const queryParams = new URLSearchParams(window.location.search);
        const id = queryParams.get('id');

        if (id == null) {
            this.setState({isCreateMode: true});
        } else {
            
            this.setState({muzikalId: id});
            db.collection("muzikals").onSnapshot((snapshot) => {
                let a = [];
                snapshot.docs.forEach(i => {
                    if(i.id === id){
                        a.push({
                            id: i.id,
                            data: i.data(),
                        });
                    }
                })
                this.setState({
                    dataDB: a.shift()
                })
            });
        }

    }

    render() {

        let {dataDB, dataNew, isCreateMode} = this.state;

        function submit() {
            if(isCreateMode)create(dataNew);
            else{
                updateMuzikal(dataDB);
                console.log("Successfully updated id = " + dataDB.id);
            } 
  
        }

        function del() {
            deleteMuzikal(dataDB.id);
        }

        function cancel() {
          //
        }

        if(isCreateMode){

            return <div className='fields'>
                <p>Create new Muzikal</p>
                <input
                  type="text"
                  placeholder="Name"
                  style={{ width: 700 }}
                  onChange={(e) => {
                    let a = dataNew;
                    a.Name = e.target.value;
                    this.setState({dataNew : a})
                  }}
                />
                <input
                  type="text"
                  placeholder="Image URL"
                  style={{ width: 700 }}
                  onChange={(e) => {
                    let a = dataNew;
                    a.SmallImageUrl = e.target.value;
                    this.setState({dataNew : a})
                  }}
                />
                <TextareaAutosize
                  placeholder="Description"
                  style={{ width: 700 }}
                  onChange={(e) => {
                    let a = dataNew;
                    a.Description = e.target.value;
                    this.setState({dataNew : a})
                  }}
                />
                <input
                  type="text"
                  placeholder="Current Price"
                  style={{ width: 700 }}
                  onChange={(e) => {
                    let a = dataNew;
                    a.CurrentPrice = e.target.value;
                    this.setState({dataNew : a})
                  }}
                />
                <input
                  type="text"
                  placeholder="Start Date"
                  style={{ width: 700 }}
                  onChange={(e) => {
                    let a = dataNew;
                    a.StartDate = e.target.value;
                    this.setState({dataNew : a})

                  }}
                />
                <input
                  type="text"
                  placeholder="End Date"
                  style={{ width: 700 }}
                  onChange={(e) => {
                    let a = dataNew;
                    a.EndDate = e.target.value;
                    this.setState({dataNew : a})
                  }}
                />
                <input
                  type="text"
                  placeholder="Running Time"
                  style={{ width: 700 }}
                  onChange={(e) => {
                    let a = dataNew;
                    a.RunningTime = e.target.value;
                    this.setState({dataNew : a})                
                  }}
                />
                <input
                  type="text"
                  placeholder="Minimum Age"
                  style={{ width: 700 }}
                  onChange={(e) => {
                    let a = dataNew; 
                    a.MinimumAge = e.target.value;
                    this.setState({dataNew : a})                 
                  }}
                />

                <Link to = '/admin'><button onClick={submit}>Submit</button></Link>
                <Link to = '/admin'><button onClick={cancel}>Cancel</button></Link>
                
            </div>
        }else{
            if(dataDB.length === 0){
                return <p>Loading...</p>
            }else{

                let muzikal = dataDB.data;
                return <div className='fields'>
                    <p>Update muzikal</p>
                    <input
                      type="text"
                      placeholder="Name"
                      value={muzikal.Name}
                      style={{ width: 700 }}
                      onChange={(e) => {
                        let a = dataDB;
                        a.data.Name = e.target.value;
                        this.setState({dataDB : a})
                      }}
                    />
                    <input
                      type="text"
                      placeholder="Image URL"
                      value={muzikal.SmallImageUrl}
                      style={{ width: 700 }}
                      onChange={(e) => {
                        let a = dataDB;
                        a.data.SmallImageUrl = e.target.value;
                        this.setState({dataDB : a})
                      }}
                    />
                    <TextareaAutosize
                      placeholder="Description"
                      defaultValue={muzikal.Description}
                      style={{ width: 700 }}
                      onChange={(e) => {
                        let a = dataDB;
                        a.data.Description = e.target.value;
                        this.setState({dataDB : a})
                        // console.log(this.state.dataDB.data.Description);
                      }}
                    />
                    <input
                      type="text"
                      placeholder="Current Price"
                      value={muzikal.CurrentPrice}
                      style={{ width: 700 }}
                      onChange={(e) => {
                        let a = dataDB;
                        a.data.CurrentPrice = e.target.value;
                        this.setState({dataDB : a})
                      }}
                    />
                    <input
                      type="text"
                      placeholder="Start Date"
                      value={muzikal.StartDate}
                      style={{ width: 700 }}
                      onChange={(e) => {
                        let a = dataDB;
                        a.data.StartDate = e.target.value;
                        this.setState({dataDB : a})

                      }}
                    />
                    <input
                      type="text"
                      placeholder="End Date"
                      value={muzikal.EndDate}
                      style={{ width: 700 }}
                      onChange={(e) => {
                        let a = dataDB;
                        a.data.EndDate = e.target.value;
                        this.setState({dataDB : a})
                        
                        // console.log(this.state.dataDB.data.Name);
                      }}
                    />
                    <input
                      type="text"
                      placeholder="Running Time"
                      value={muzikal.RunningTime}
                      style={{ width: 700 }}
                      onChange={(e) => {
                        let a = dataDB;
                        a.data.RunningTime = e.target.value;
                        this.setState({dataDB : a})                      
                        // console.log(this.state.dataDB.data.Name);
                      }}
                    />
                    <input
                      type="text"
                      placeholder="Minimum Age"
                      value={muzikal.MinimumAge}
                      style={{ width: 700 }}
                      onChange={(e) => {
                        let a = dataDB; 
                        a.data.MinimumAge = e.target.value;
                        this.setState({dataDB : a})                      
                        // console.log(this.state.dataDB.data.Name);
                      }}
                    />

                    <Link to = '/admin'><button onClick={submit}>Submit</button></Link>
                    <Link to = '/admin'><button onClick={cancel}>Cancel</button></Link>
                    <Link to = '/admin'><button onClick={del}>Delete</button></Link>
                    
                </div>
            }
        }   
            
    }

}

export default MuzikalEdit;