'use client';
import { useState, ChangeEvent } from 'react';

export default function Names() {
    const [inputName, setInputName] = useState('');
    const [name, setName] = useState('');

    const onChange = (e:ChangeEvent<HTMLInputElement>) => {
        setInputName(e.target.value);
    }

    const addPerson = () => {
        setName(inputName);
    }

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
            {name && <div className="form_footer">
                {name}
            </div>}
        </form>
    )
}