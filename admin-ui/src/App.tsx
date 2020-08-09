import React, {useEffect, useState} from 'react';
import './App.css';
import List from './component/List';
import LogDispalyer from './component/LogDispalyer';
import withListLoading from './component/withListLoading';
import Container from "react-bootstrap/Container";
import Col from 'react-bootstrap/Col'
import Row from 'react-bootstrap/Row'

function App() {
    const ListLoading = withListLoading(List);
    const LogDispalyerLoading = withListLoading(LogDispalyer);

    const [listenersState, setListenersState] = useState({
        loading: false,
        listeners: null,
        listenerForLogs: null,
    });
    const [logState, setLogState] = useState({
        loading: false,
        logs: null
    });

    useEffect(() => {
        setListenersState({loading: true, listeners: null, listenerForLogs: null});
        const apiUrl = `http://localhost:1234/configuration`;
        fetch(apiUrl)
            .then((res) => res.json())
            .then((listeners) => {
                setListenersState({loading: false, listeners: listeners, listenerForLogs: null});
            });
    }, [setListenersState]);

    useEffect(() => {
        setLogState({loading: true, logs: null});
        const apiUrl = `http://localhost:1234/logs`;
        fetch(apiUrl)
            .then((res) => res.json())
            .then((logs) => {
                setLogState({loading: false, logs: logs});
            });
    }, [setLogState]);

    return (
        <Container className="min-100">
            <div className="navbar navbar-dark bg-dark box-shadow">
                <div className="container d-flex justify-content-between">
                    <a href="#" className="navbar-brand d-flex align-items-center">
                        <strong>Hecate - Admin UI</strong>
                    </a>
                </div>
            </div>
            <Row>
                <Col>
                    <ListLoading isLoading={listenersState.loading} repos={listenersState.listeners}/>
                </Col>
            </Row>
            <Row>
                <Col>
                    <LogDispalyerLoading isLoading={logState.loading} repos={logState.logs}/>
                </Col>
            </Row>
            <footer>
                This is a footer
            </footer>
        </Container>
    )
}

export default App;
