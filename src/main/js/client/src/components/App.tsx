import React from 'react';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';

import Homepage from './HomePage';
import TokenConfirmPage from './TokenConfirmPage';

const App: React.FC<{}> = () => {
  return (
    <Router>
      <Switch>
        <Route path="/email-confirmation" component={TokenConfirmPage} />
        <Route path="/" component={Homepage} />
      </Switch>
    </Router>
  )
};

export default App;