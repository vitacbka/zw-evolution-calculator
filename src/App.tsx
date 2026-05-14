import React, { useState, useEffect } from 'react';
import { DAYS } from './logic/calculator';
import type { Reward } from './types';
import { motion, AnimatePresence } from 'framer-motion';
import { ChevronDown, Heart, Info, RotateCcw, CreditCard, Wallet, Download, X } from 'lucide-react';
import './App.css';

const App: React.FC = () => {
  const [selectedDayIndex, setSelectedDayIndex] = useState(0);
  const [inputs, setInputs] = useState<{ [key: number]: number[] }>({});
  const [points, setPoints] = useState(0);
  const [rewards, setRewards] = useState<Reward[]>([]);
  const [isTopRewardOpen, setIsTopRewardOpen] = useState(false);
  
  // PWA Logic
  const [deferredPrompt, setDeferredPrompt] = useState<any>(null);
  const [showIOSPrompt, setShowIOSPrompt] = useState(false);
  const [isInstallable, setIsInstallable] = useState(false);

  useEffect(() => {
    window.addEventListener('beforeinstallprompt', (e) => {
      e.preventDefault();
      setDeferredPrompt(e);
      setIsInstallable(true);
    });

    // Check if it's iOS
    const isIOS = /iPad|iPhone|iPod/.test(navigator.userAgent) && !(window as any).MSStream;
    const isStandalone = window.matchMedia('(display-mode: standalone)').matches;
    if (isIOS && !isStandalone) {
      setIsInstallable(true);
    }
  }, []);

  const handleInstallClick = async () => {
    const isIOS = /iPad|iPhone|iPod/.test(navigator.userAgent) && !(window as any).MSStream;
    
    if (isIOS) {
      setShowIOSPrompt(true);
    } else if (deferredPrompt) {
      deferredPrompt.prompt();
      const { outcome } = await deferredPrompt.userChoice;
      if (outcome === 'accepted') {
        setDeferredPrompt(null);
        setIsInstallable(false);
      }
    }
  };

  const selectedDay = DAYS[selectedDayIndex];

  useEffect(() => {
    const currentInputs = inputs[selectedDayIndex] || new Array(selectedDay.inputHints.length).fill(0);
    const calculatedPoints = selectedDay.calculatePoints(currentInputs);
    setPoints(calculatedPoints);
    setRewards(selectedDay.getRewards(calculatedPoints));
  }, [selectedDayIndex, inputs, selectedDay]);

  const handleInputChange = (index: number, value: string) => {
    const cleanValue = value.replace(/\D/g, '');
    const numValue = parseInt(cleanValue) || 0;
    
    setInputs(prev => {
      const currentInputs = [...(prev[selectedDayIndex] || new Array(selectedDay.inputHints.length).fill(0))];
      currentInputs[index] = numValue;
      return { ...prev, [selectedDayIndex]: currentInputs };
    });
  };

  const clearAll = () => {
    if (window.confirm('Вы уверены, что хотите очистить все поля?')) {
      setInputs({});
    }
  };

  const normalRewards = rewards.filter(r => !r.isTopReward);
  const topReward = rewards.find(r => r.isTopReward);

  return (
    <div className="app-container">
      <motion.header 
        initial={{ y: -50, opacity: 0 }}
        animate={{ y: 0, opacity: 1 }}
        transition={{ duration: 0.5 }}
        style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', position: 'relative' }}
      >
        <h1 style={{ fontSize: '1.6rem', margin: '20px 0' }}>ZW Evolution calculator 🧟</h1>
        {isInstallable && (
          <button 
            onClick={handleInstallClick}
            className="install-btn"
            title="Установить приложение"
          >
            <Download size={20} />
          </button>
        )}
      </motion.header>

      <AnimatePresence>
        {showIOSPrompt && (
          <motion.div 
            className="ios-prompt-overlay"
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
            onClick={() => setShowIOSPrompt(false)}
          >
            <motion.div 
              className="ios-prompt"
              initial={{ scale: 0.8, y: 20 }}
              animate={{ scale: 1, y: 0 }}
              onClick={(e) => e.stopPropagation()}
            >
              <button className="close-prompt" onClick={() => setShowIOSPrompt(false)}>
                <X size={20} />
              </button>
              <h3>Установка на iOS</h3>
              <p>Чтобы установить калькулятор на рабочий стол:</p>
              <ol>
                <li>Нажмите кнопку <strong>«Поделиться»</strong> (иконка квадрата со стрелкой вверх внизу экрана).</li>
                <li>Прокрутите список и выберите <strong>«На экран Домой»</strong>.</li>
              </ol>
            </motion.div>
          </motion.div>
        )}
      </AnimatePresence>

      <main>
        <motion.div 
          className="card"
          initial={{ x: -20, opacity: 0 }}
          animate={{ x: 0, opacity: 1 }}
          transition={{ delay: 0.2 }}
        >
          <label htmlFor="day-select">Выберите день эволюции</label>
          <select 
            id="day-select" 
            value={selectedDayIndex} 
            onChange={(e) => {
              setSelectedDayIndex(parseInt(e.target.value));
              setIsTopRewardOpen(false);
            }}
          >
            {DAYS.map((day, index) => (
              <option key={day.id} value={index}>{day.name}</option>
            ))}
          </select>
        </motion.div>

        <motion.div 
          className="card"
          initial={{ x: 20, opacity: 0 }}
          animate={{ x: 0, opacity: 1 }}
          transition={{ delay: 0.3 }}
        >
          <div className="inputs-grid">
            {selectedDay.inputHints.map((hint, index) => (
              <div key={index} className="input-group">
                <label>{hint}</label>
                <input 
                  type="text" 
                  inputMode="numeric"
                  pattern="[0-9]*"
                  value={inputs[selectedDayIndex]?.[index] || ''} 
                  placeholder="0"
                  onChange={(e) => handleInputChange(index, e.target.value)}
                />
              </div>
            ))}
          </div>
          <button className="clear-btn" onClick={clearAll}>
            <RotateCcw size={16} style={{ marginRight: '8px' }} />
            Очистить все
          </button>
        </motion.div>

        <motion.div 
          className="result-card"
          initial={{ scale: 0.9, opacity: 0 }}
          animate={{ scale: 1, opacity: 1 }}
          transition={{ delay: 0.4 }}
        >
          <h2>Результат</h2>
          <div className="points-display">
            <span className="points-value">{points.toLocaleString()}</span>
            <span className="points-label">очков</span>
          </div>
        </motion.div>

        {selectedDay.showDay6Probabilities && points > 0 && (
          <motion.div 
            className="card probability-card"
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
          >
            <div style={{ display: 'flex', alignItems: 'center', gap: '8px', color: 'var(--blue)', marginBottom: '12px' }}>
              <Info size={18} />
              <h3 style={{ margin: 0, fontSize: '1rem' }}>Прогноз (День 6)</h3>
            </div>
            
            <div style={{ fontSize: '0.85rem', color: 'var(--text-dim)', display: 'flex', flexDirection: 'column', gap: '8px' }}>
              <div>
                Ожидаемое кол-во синих из зеленых: 
                <strong style={{ color: 'var(--blue)', marginLeft: '5px' }}>
                  ~{Math.round((inputs[5]?.[1] || 0) * 0.05)} шт.
                </strong>
              </div>
              <div>
                Ожидаемое кол-во фиол. из синих: 
                <strong style={{ color: 'var(--accent-variant)', marginLeft: '5px' }}>
                  ~{Math.round((inputs[5]?.[2] || 0) * 0.04)} шт.
                </strong>
              </div>
              <div style={{ marginTop: '5px', borderTop: '1px solid rgba(127, 180, 202, 0.2)', paddingTop: '8px' }}>
                Потенциальные доп. очки: 
                <strong style={{ color: 'var(--yellow)', marginLeft: '5px' }}>
                  ~{Math.round(
                    ((inputs[5]?.[1] || 0) * 0.05 * 30) + 
                    ((inputs[5]?.[2] || 0) * 0.04 * 250)
                  ).toLocaleString()}
                </strong>
              </div>
            </div>
          </motion.div>
        )}

        <section className="rewards-section">
          <h3 style={{ color: 'var(--yellow)', marginBottom: '15px' }}>🎁 Награды</h3>
          
          <div className="rewards-container">
            {normalRewards.length === 0 ? (
              <p className="no-rewards">Недостаточно очков для обычных наград</p>
            ) : (
              normalRewards.map((reward, index) => (
                <motion.div 
                  key={index} 
                  className="reward-item"
                  initial={{ opacity: 0, y: 10 }}
                  animate={{ opacity: 1, y: 0 }}
                  transition={{ delay: index * 0.1 }}
                >
                  <div className="reward-info">Награда за {reward.points.toLocaleString()} очков</div>
                  <img src={reward.imagePath} alt={`Награда ${reward.points}`} />
                </motion.div>
              ))
            )}
          </div>

          {topReward && (
            <div className={`accordion ${isTopRewardOpen ? 'open' : ''}`} style={{ marginTop: '20px' }}>
              <div className="accordion-header" onClick={() => setIsTopRewardOpen(!isTopRewardOpen)}>
                <div style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
                  <span style={{ color: 'var(--yellow)', fontWeight: 'bold' }}>👑 Награды за топ</span>
                </div>
                <ChevronDown className="accordion-icon" />
              </div>
              <AnimatePresence>
                {isTopRewardOpen && (
                  <motion.div 
                    className="accordion-content"
                    initial={{ height: 0, opacity: 0 }}
                    animate={{ height: 'auto', opacity: 1 }}
                    exit={{ height: 0, opacity: 0 }}
                  >
                    <div className="reward-item top-reward">
                      <img src={topReward.imagePath} alt="Топ награда" />
                    </div>
                  </motion.div>
                )}
              </AnimatePresence>
            </div>
          )}
        </section>

        <motion.div 
          className="support-block"
          initial={{ y: 30, opacity: 0 }}
          whileInView={{ y: 0, opacity: 1 }}
          viewport={{ once: true }}
        >
          <div className="support-title">
            <Heart className="heart-icon" fill="var(--red)" />
            Поддержать разработчика
          </div>
          <p style={{ fontSize: '0.85rem', color: 'var(--text-dim)', marginBottom: '20px' }}>
            Если калькулятор помог вам, вы можете поддержать проект
          </p>
          <div className="donate-buttons">
            <a href="https://yoomoney.ru/to/41001957756369/0" target="_blank" rel="noopener noreferrer" className="donate-btn yoomoney">
              <Wallet size={18} />
              ЮMoney
            </a>
            <a href="https://www.sberbank.ru/ru/choise_bank?requisiteNumber=79217884626&bankCode=100000000111" target="_blank" rel="noopener noreferrer" className="donate-btn sber">
              <CreditCard size={18} />
              Сбербанк
            </a>
          </div>
        </motion.div>
      </main>

      <div style={{ padding: '20px' }}></div>
    </div>
  );
};

export default App;
