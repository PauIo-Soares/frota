/* Materialize Fallback JS (leve) */
(function(){
  function ready(fn){ if(document.readyState!='loading'){fn();} else {document.addEventListener('DOMContentLoaded', fn);} }
  function toast(html){ var t=document.createElement('div'); t.className='toast'; t.textContent=html; document.body.appendChild(t); setTimeout(function(){ t.remove(); }, 2500); }

  // Exponho um M mínimo para não quebrar inicializações
  window.M = window.M || {};
  M.Dropdown = { init: function(els){
      (els||document.querySelectorAll('.dropdown-trigger')).forEach(function(el){
        var target = el.getAttribute('data-target');
        var menu = document.getElementById(target) || document.querySelector('#'+target);
        if(!menu) return;
        el.addEventListener('click', function(ev){ ev.preventDefault(); ev.stopPropagation(); menu.style.display = (menu.style.display==='block'?'none':'block'); });
        document.addEventListener('click', function(){ menu.style.display='none'; });
      });
    }
  };
  M.Sidenav = { init: function(els){
      (els||document.querySelectorAll('.sidenav')).forEach(function(el){
        var triggers = document.querySelectorAll('.sidenav-trigger');
        triggers.forEach(function(tr){ tr.addEventListener('click', function(ev){ ev.preventDefault(); el.classList.toggle('open'); }); });
      });
    }
  };
  M.FormSelect = { init: function(els){ /* nativo: nenhum wrapper necessário */ } };
  M.Tooltip = { init: function(){ /* no-op */ } };
  M.toast = function(o){ toast(typeof o==='string'?o:(o&&o.html)||''); };

  // Floating labels simples: adiciona .not-empty
  function updateNotEmpty(){
    document.querySelectorAll('.input-field input, .input-field textarea, .input-field select').forEach(function(i){
      if(i.value && i.value.toString().length>0){ i.classList.add('not-empty'); } else { i.classList.remove('not-empty'); }
    });
  }

  ready(function(){
    M.Dropdown.init();
    M.Sidenav.init();
    M.FormSelect.init();
    M.Tooltip.init();
    updateNotEmpty();
    document.addEventListener('input', function(e){ if(e.target && e.target.closest('.input-field')) updateNotEmpty(); });
  });
})();