'use client';
import { useState, ChangeEvent } from 'react';

export default function Names() {
    const [inputName, setInputName] = useState('');
    const [loading, setLoading] = useState(false);
    const [name, setName] = useState('');

    const onChange = (e:ChangeEvent<HTMLInputElement>) => {
        setInputName(e.target.value);
    }

    const setNameResult = (name: string) => {
        setLoading(!!!name);
        setName(name);
    }

    const addPerson = () => {
        setNameResult('');
        fetch('http://localhost:8080/names', {
            method: "POST",
            body: JSON.stringify({
                name: inputName
            }),
            headers: {
                'Content-type': 'application/json',
            },
        })
        .then(response => fetch(response.headers.get("Location")!))
        .then(response => response.json())
        .then(n => {
            setNameResult(n.name);
        });
    };

    return (
        <form className="form">
            <div className="mb-4">
                <label className="label" htmlFor="name">
                    Name
                </label>
                <input className="input" id="name" value={inputName} onChange={onChange} type="text" placeholder="Name"/>
            </div>
            <div className="form_footer">
                <button className="btn" onClick={addPerson} type="button">
                    Add Person
                </button>
            </div>
            {loading && <div className="form_footer">loading...</div>}
            {name && <div className="form_footer">
                {name}
            </div>}
        </form>
    )
}