'use client';
import { useState, useMemo, ChangeEvent, FormEvent } from 'react';
import dynamic from 'next/dynamic';

function Names() {
    const [inputName, setInputName] = useState('');
    const [loading, setLoading] = useState(false);
    const [name, setName] = useState('');

    const config = useMemo(() => fetch('config.json')
        .then(response => response.json()), []);

    const onChange = (e:ChangeEvent<HTMLInputElement>) => {
        setInputName(e.target.value);
    }

    const setNameResult = (name: string) => {
        setLoading(!!!name);
        setName(name);
    }

    const addPerson = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        setNameResult('');
        
        config
            .then(c => 
                fetch(`${c.SERVER_URL}/names`, {
                    method: "POST",
                    body: JSON.stringify({
                        name: inputName
                    }),
                    headers: {
                        'Content-type': 'application/json',
                    },
                }))
            .then(response => fetch(response.headers.get("Location")!))
            .then(response => response.json())
            .then(n => {
                setNameResult(n.name);
            });
    };

    return (
        <form className="form" onSubmit={addPerson}>
            <div className="mb-4">
                <label className="label" htmlFor="name">
                    Name
                </label>
                <input className="input" id="name" value={inputName} onChange={onChange} type="text" placeholder="Name"/>
            </div>
            <div>
                <button className="btn" type="submit">
                    Add Person
                </button>
            </div>
            {loading && <div className="result">loading...</div>}
            {name && <div className="result">
                {name}
            </div>}
        </form>
    )
}

export default dynamic(() => Promise.resolve(Names), { ssr: false });