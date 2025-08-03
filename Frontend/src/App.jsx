import React from 'react';
import Navbar from './components/navbar';
import Hero from './components/hero';
import Explore from './components/Explore';
import bgImage from './assets/bg.png';

function App() {
  return (
    <div
      className="bg-brand-background min-h-screen font-inter bg-right-top bg-no-repeat"
      style={{ backgroundImage: `url(${bgImage})` }}
    >
      <Navbar />
      <main className="container mx-auto py-8 md:px-10">
        <div className="space-y-4">
          <Hero />
          <Explore />
        </div>
      </main>
    </div>
  );
}

export default App;
