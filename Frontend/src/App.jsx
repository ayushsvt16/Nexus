import React from 'react';
import Navbar from './components/navbar';
import Hero from './components/hero';
import Explore from './components/Explore';
import Comingsoon from './components/comingsoon';


function App() {
  return (
    <div
      className="bg-brand-background min-h-screen font-inter bg-right-top bg-no-repeat bg-none md:bg-[url('./assets/bg.png')] "     
    >
      <Navbar />
      <main className="container mx-auto py-8 md:px-10 px-4">
        <div className="space-y-4">
          <Hero />
          <Explore />
        </div>
      </main>
      <div className="mx-12 mt-10">
        <Comingsoon />
      </div>
    </div>
  );
}

export default App;
