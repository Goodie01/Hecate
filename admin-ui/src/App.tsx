import React, {useEffect, useState} from 'react';
import './App.css';

import List from './component/List';
import withListLoading from './component/withListLoading';

function App() {
    const ListLoading = withListLoading(List);
    const [appState, setAppState] = useState({
        loading: false,
        listeners: null,
    });

    useEffect(() => {
        setAppState({loading: true, listeners: null});
        const apiUrl = `http://localhost:1234/configuration`;
        fetch(apiUrl)
            .then((res) => res.json())
            .then((listeners) => {
                setAppState({loading: false, listeners: listeners});
            });
    }, [setAppState]);

    return (
        <div className='App'>
            <div className='container'>
                <h1>My Repositories</h1>
            </div>
            <div className='repo-container'>
                <ListLoading isLoading={appState.loading} repos={appState.listeners}/>
            </div>
            <footer>
                <div className='footer'>
                    Built{' '}
                    <span role='img' aria-label='love'>
            💚
          </span>{' '}
                    with by Shedrack Akintayo
                </div>
            </footer>
        </div>
    );
}

export default App;
