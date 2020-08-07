import React, {useEffect, useState} from 'react';
import './App.css';
import List from './component/List';
import LogDispalyer from './component/LogDispalyer';
import withListLoading from './component/withListLoading';
import {log} from "util";


function App() {
    const ListLoading = withListLoading(List);
    const LogDispalyerLoading = withListLoading(LogDispalyer);

    const [listenersState, setListenersState] = useState({
        loading: false,
        listeners: null,
        listenerForLogs:null,
    });
    const [logState, setLogState] = useState({
        loading: false,
        logs: null
    });

    useEffect(() => {
        setListenersState({loading: true, listeners: null, listenerForLogs: null });
        const apiUrl = `http://localhost:1234/configuration`;
        fetch(apiUrl)
            .then((res) => res.json())
            .then((listeners) => {
                setListenersState({loading: false, listeners: listeners, listenerForLogs: null });
            });
    }, [setListenersState]);

    useEffect(() => {
        setLogState({ loading: true, logs: null});
        const apiUrl = `http://localhost:1234/logs`;
        fetch(apiUrl)
            .then((res) => res.json())
            .then((logs) => {
                setLogState({ loading: false, logs: logs});
            });
    }, [setLogState]);

    return (

        <div className='App'>
            <div className='container'>
                <h1 className="header">Welcome to Hecate - Admin UI</h1>
            </div>
            <div className='listeners-container'>
                <ListLoading isLoading={listenersState.loading} repos={listenersState.listeners}/>
            </div>
            <div className='logs-container'>
                <LogDispalyerLoading isLoading={logState.loading} repos={logState.logs}/>
            </div>
            <footer>
                This is a footer
            </footer>
        </div>
    )
}

export default App;
