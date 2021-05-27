import React, {useState, useEffect} from 'react';
import axios from "axios";

function ViewPending(props){
    const [data, setData] = useState([]);
    function back() {
        props.setNav("NA")
    }

    useEffect(() => {
        query().then(r => console.log(r));
        // return () => {

        // }
    }, [])

    async function query(){
        let username = props.username;
        let password = "";
        await axios({
            method: 'get',
            url: "http://localhost:9999/pending",
            params: {username,password},
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

    function getFormattedDate(date) {
        let year = date.getFullYear();

        let month = (1 + date.getMonth()).toString();
        month = month.length > 1 ? month : '0' + month;

        let day = date.getDate().toString();
        day = day.length > 1 ? day : '0' + day;

        return  year+ '-' + month + '-' +day ;
    }


    return(
        <div>
            <button  className="close-button" onClick={back}>X</button>
            <h5>View Pending Reimbursement Requests</h5>
            
            {/*<button onClick={query} className="link">Find Pending Reimbursements</button>*/}

            {/* <Reimbursement data={data}/> */}

        {/*    {data && data.map((value,index)=>{*/}
        {/*    console.log(value.username+ value.amount+ value.title+ value.amount);*/}
        {/*    <Reimbursement username={value.username} />*/}
        {/*})}*/}
        <div className="scroll">
            {data.map(d=>{
                return (
                    <div className="view-pending" key={d.id}> title: {d.title}, amount: {d.amount},date: {getFormattedDate(new Date(d.date))} , approved: {d.approved} </div>
                )
            })

            }
        </div>


        </div>



    );
}

export default ViewPending;