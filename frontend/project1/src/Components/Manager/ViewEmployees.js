import React, {useState} from 'react';
import axios from "axios";
function ViewEmployees(props){
    const [name, setName] = useState("*");
    const [data, setData] = useState([]);

    function back() {
        props.setNav("NA")
    }

    React.useEffect(()=>{
        query(name).then(r => console.log(r));
    },[]);

    React.useEffect(()=>{
        if(name.length===0){setName("*")}
        query(name);
        console.log("hello??");
    },[name]);

    async function query(name){
        //display all employees
        await axios({
            method: 'get',
            url: "http://localhost:9999/getEmployees",
            params:{name},
            headers : {
                'Content-Type': 'application/json'
            }
        }).then(res => {
            if(res.status===200){
                console.log(res.data);
                setData(res.data);
                // console.log(data.entries());
            }
            // if(res.status===401)
            else{
                console.log("here");
                alert("nothing found");
            }
        })
            .catch(err => console.log(err));
    }

    return (
        <div >
            <button onClick={back}>X</button>
            <h5>View Employees</h5>
            <div className="filter">
                <h5 >Filter by username:</h5>
                <input type="text" onChange={e=>setName(e.target.value)}/>
            </div>
            <div className="scroll">
                {data.map(d=>{
                    return (
                        <div className="view-pending" key={d.username}> username: {d.username}, email: {d.email} </div>
                    )
                    })
                }
            </div>


        </div>
    );

}
export default ViewEmployees;