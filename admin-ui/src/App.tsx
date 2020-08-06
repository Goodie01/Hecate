import React, {useEffect, useState} from 'react';
import './App.css';
import {MemoryRouter} from 'react-router-dom';

import Jumbotron from 'react-bootstrap/Jumbotron';

import List from './component/List';
import withListLoading from './component/withListLoading';
import {listeners} from "cluster";


function App() {
    const ListLoading = withListLoading(List);
    const [appState, setAppState] = useState({
        loading: false,
        listeners: null,
        logs: null
    });

    useEffect(() => {
        setAppState({loading: true, listeners: null, logs: null});
        const apiUrl = `http://localhost:1234/configuration`;
        fetch(apiUrl)
            .then((res) => res.json())
            .then((listeners) => {
                setAppState({loading: false, listeners: listeners, logs:null});
            });
    }, [setAppState]);

    useEffect(() => {
        setAppState({loading: true, listeners: appState.listeners, logs: null});
        const apiUrl = `http://localhost:1234/logs`;
        fetch(apiUrl)
            .then((res) => res.json())
            .then((listeners) => {
                setAppState({loading: false, listeners: listeners});
            });
    }, [setAppState]);

    return (

        <div className='App'>
            <div className='container'>
                <h1 className="header">Welcome to Hecate - Admin UI</h1>
            </div>
            <div className='repo-container'>
                <ListLoading isLoading={appState.loading} repos={appState.listeners}/>
            </div>
            <footer>
                This is a footer
            </footer>
        </div>
    )
}

export default App;
