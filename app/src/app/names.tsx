'use client';
import { useState, useMemo, ChangeEvent, FormEvent } from 'react';
import dynamic from 'next/dynamic';

type FullName = {firstName: string, lastName: string};
const DefaultFullName: FullName | null = null;
const InitialFullName: FullName = { firstName: '', lastName: '' };

function Names() {
    const [inputName, setInputName] = useState(DefaultFullName);
    const [loading, setLoading] = useState(false);
    const [name, setName] = useState(DefaultFullName);

    const config = useMemo(() => fetch('config.json')
        .then(response => response.json()), []);

    const onChange = (e:ChangeEvent<HTMLInputElement>) => {
        setInputName({ ...(inputName || InitialFullName), [e.target.id]: e.target.value});
    }

    const setNameResult = (name: FullName | null) => {
        setLoading(!!!name);
        setName(name || DefaultFullName);
    }

    const addPerson = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        setNameResult(null);
        
        config
            .then(c => 
                fetch(`${c.SERVER_URL}/names`, {
                    method: "POST",
                    body: JSON.stringify({
                        fullName: inputName
                    }),
                    headers: {
                        'Content-type': 'application/json',
                    },
                }))
            .then(response => fetch(response.headers.get("Location")!))
            .then(response => response.json())
            .then(n => {
                setNameResult(n.fullName);
            });
    };

    return (
        <form className="form" onSubmit={addPerson}>
            <div className="mb-4">
                <label className="label" htmlFor="firstName">
                    Firstname
                </label>
                <input className="input" id="firstName" value={inputName && inputName.firstName || ''} onChange={onChange} type="text" placeholder="Name"/>
            </div>
            <div className="mb-4">
                <label className="label" htmlFor="lastName">
                    Lastname
                </label>
                <input className="input" id="lastName" value={inputName && inputName.lastName || ''} onChange={onChange} type="text" placeholder="Name"/>
            </div>
            <div>
                <button className="btn" type="submit">
                    Add Person
                </button>
            </div>
            {loading && <div className="result">loading...</div>}
            {name && <div className="result">
                {name.firstName} {name.lastName}
            </div>}
        </form>
    )
}

export default dynamic(() => Promise.resolve(Names), { ssr: false });