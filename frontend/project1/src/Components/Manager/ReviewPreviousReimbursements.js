import React, {useState} from 'react';
import axios from "axios";
function ReviewPreviousReimbursements(props){
    const [data, setData] = useState([]);
    const [name, setName] = useState("*");
    React.useEffect(()=>{},[]);
    React.useEffect(()=>{
        // console.log("code was here");
        query(name);
    },[])

    React.useEffect(()=>{
        if(name.length===0){setName("*")}
        query(name);
    },[name]);

    function back() {
        props.setNav("NA")
    }

    async function query(name){
        await axios({
            method: 'get',
            url: "http://localhost:9999/allPrevReimbursements",
            params:{name},
            headers : {
                'Content-Type': 'application/json'
            }
        }).then(res => {
            if(res.status===200){
                console.log(res.data);
                // setData(res.data);
                // console.log(data.entries());
                setData(res.data);
            }
            // if(res.status===401)
            else{
                console.log("here");
                alert("nothing found");
            }
        })
            .catch(err => console.log(err));

    }


    function getFormattedDate(date) {
        let year = date.getFullYear();

        let month = (1 + date.getMonth()).toString();
        month = month.length > 1 ? month : '0' + month;

        let day = date.getDate().toString();
        day = day.length > 1 ? day : '0' + day;

        return  year+ '-' + month + '-' +day ;
    }

    return (
        <div>
            <button onClick={back}>X</button>
            <h5>Review Previous Reimbursements</h5>
            <div className="filter">
                <h5 >Filter by username:</h5>
                <input type="text" onChange={e=>setName(e.target.value)}/>
            </div>
            <div className="scroll">
                {data.map(d=>{
                    return (
                        <div className="view-pending" key={d.id}> Username : {d.username}; Title: {d.title};
                            Amount: {d.amount}; Date: {getFormattedDate(new Date(d.date))} ; approval : {d.approved};
                            deciding manager : {d.approvedBy}
                        </div>
                    )
                })
                }
            </div>
        </div>
    );

}
export default ReviewPreviousReimbursements;