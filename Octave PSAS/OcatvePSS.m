%Resources/ Works Cited
%https://www.youtube.com/watch?v=aD8k4pYUBOk
%https://en.wikibooks.org/wiki/Octave_Programming_Tutorial/Getting_started
%https://en.wikibooks.org/wiki/Octave_Programming_Tutorial
%https://en.wikibooks.org/wiki/Octave_Programming_Tutorial/Plotting
%https://octave.sourceforge.io/data-smoothing/function/regdatasmooth.html



% Parameters for the quadratic equation y = ax^2 + bx + c
a = 1.0;
b = 2.0;
c = 1.0;
interval = 0.5;
range = -20:interval:20; % Similar range as the Java program

% Quadratic Plotter
x = range;
y = a * x.^2 + b * x + c;
figure;
scatter(x, y);
title('Quadratic Function');
xlabel('X');
ylabel('Y');

% Quadratic Salter
salt_range = 50.0;
y_salted = y + (rand(size(y)) * 2 - 1) * salt_range;
figure;
scatter(x, y_salted);
title('Salted Quadratic Function');
xlabel('X');
ylabel('Y_Salted');

% Quadratic Smoother
window_value = 3;
y_smoothed = filter(ones(1, window_value) / window_value, 1, y_salted);
figure;
scatter(x, y_smoothed);
title('Smoothed Quadratic Function');
xlabel('X');
ylabel('Y_Smoothed');

% Quadratic Smoother 2
window_value = 3;
y_smoothed = filter(ones(1, window_value) / window_value, 1, y_smoothed);
figure;
scatter(x, y_smoothed);
title('Smoothed Quadratic Function');
xlabel('X');
ylabel('Y_Smoothed');

% Quadratic Smoother 3
window_value = 3;
y_smoothed = filter(ones(1, window_value) / window_value, 1, y_smoothed);
figure;
scatter(x, y_smoothed);
title('Smoothed Quadratic Function');
xlabel('X');
ylabel('Y_Smoothed');