import React from 'react';
const List = (props) => {
    const { repos: listeners } = props;
    if (!listeners || listeners.length === 0) return <p>No repos, sorry</p>;
    return (
        <ul>
            <h2 className='list-head'>Listeners</h2>
            {listeners.map((listener) => {
                return (
                    <li key={listener.id} className='list'>
                        <span className='listener-name'>{listener.id} </span>
                        <span className='listener-port'>{listener.port}</span>
                        <span className='listener-method'>{listener.httpMethod}</span>
                        <span className='listener-context'>{listener.context}</span>
                    </li>
                );
            })}
        </ul>
    );
};
export default List;