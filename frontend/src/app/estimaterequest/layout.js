// estimaterequest/layout.js

import React from 'react';

const Layout = ({ children }) => {
    return (
        <div>
            <header>
                <nav>
                    <ul>
                        <li><a href="/">Home</a></li>
                        <li><a href="/estimaterequest/page">Estimate Requests</a></li>
                    </ul>
                </nav>
            </header>
            <main>
                {children}
            </main>
            <footer>
                <p>&copy; 2025 My Estimate App</p>
            </footer>
        </div>
    );
};

export default Layout;
