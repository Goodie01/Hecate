import React, {useEffect, useState} from 'react';
import './App.css';
import List from './component/List';
import LogDispalyer from './component/LogDispalyer';
import withListLoading from './component/withListLoading';


function App() {
    const ListLoading = withListLoading(List);
    const LogDispalyerLoading = withListLoading(LogDispalyer);
    //TODO
    //  create two useStates, one for listeners, one for logs????
    const [appState, setAppState] = useState({
        loadingListeners: false,
        loadingLogs: false,
        listeners: null,
        listenerForLogs:null,
        logs: null
    });

    useEffect(() => {
        appState.loadingListeners = true;
        const apiUrl = `http://localhost:1234/configuration`;
        fetch(apiUrl)
            .then((res) => res.json())
            .then((listeners) => {
                appState.listeners = listeners;
                appState.listenerForLogs = null;
                appState.loadingListeners = false;
            });
    }, [appState.listeners, appState.listenerForLogs]);

    useEffect(() => {
        appState.loadingLogs = true;
        const apiUrl = `http://localhost:1234/logs`;
        fetch(apiUrl)
            .then((res) => res.json())
            .then((logs) => {
                appState.logs = logs;
                appState.loadingLogs = false;
            });
    }, [appState.logs, appState.loadingLogs]);

    return (

        <div className='App'>
            <div className='container'>
                <h1 className="header">Welcome to Hecate - Admin UI</h1>
            </div>
            <div className='listeners-container'>
                <ListLoading isLoading={appState.loadingListeners} repos={appState.listeners}/>
            </div>
            <div className='logs-container'>
                <LogDispalyerLoading isLoading={appState.loadingLogs} repos={appState.logs}/>
            </div>
            <footer>
                This is a footer
            </footer>
        </div>
    )
}

export default App;
